package org.example.userstask.web.controller;

import org.example.userstask.entity.User;
import org.example.userstask.services.DataTestValues;
import org.example.userstask.services.UserComponent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser("user")
class UserControllerTest {

    @Autowired
    private UserComponent component;
    @Autowired
    private DataTestValues testValues;
    @Autowired
    private MockMvc mockMvc;
    @LocalServerPort
    private int port;

    @Test
    void findByIdOrDie()  {
        try {
                this.mockMvc.perform(get("http://localhost:" + port + "/api/users/1" ))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.username", is(testValues.testNameNew)))
                        .andExpect(jsonPath("$.email", is(testValues.testEmailNew)))
                        .andExpect(jsonPath("$.password", is(testValues.testPassword)))
                        .andExpect(jsonPath("$.createdAt", is(testValues.testCreatedAt)))
                        .andExpect(jsonPath("$.updatedAt", is(testValues.testUpdatedAt)));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void commit(){
        long id = getIdCommitedUser();
        User userFromTable = component.findByIdOrDie(id);
        LocalDateTime time = LocalDateTime.now();
        assertNotNull(userFromTable);
        assertEquals(testValues.testName, userFromTable.getUsername());
        assertEquals(testValues.testPassword, userFromTable.getPassword());
        assertEquals(testValues.testEmail, userFromTable.getEmail());
        assertEquals(time.getHour(), userFromTable.getCreatedAt().getHour());
        assertEquals(time.getMinute(), userFromTable.getCreatedAt().getMinute());
        assertEquals(time.getHour(), userFromTable.getUpdatedAt().getHour());
        assertEquals(time.getMinute(), userFromTable.getUpdatedAt().getMinute());
        component.deleteUserById(id);
    }

    @Test
    void findAll() {
        try{
            mockMvc.perform(get("http://localhost:" + port + "/api/users"))
                .andExpect(jsonPath("$", hasSize(1)));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void deleteUserById() {
        try{
            mockMvc.perform(get("http://localhost:" + port + "/api/users"))
                .andExpect(jsonPath("$", hasSize(1)));

            long id = getIdCommitedUser();

            mockMvc.perform(get("http://localhost:" + port + "/api/users"))
                    .andExpect(jsonPath("$", hasSize(2)));

            this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/users/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
            mockMvc.perform(get("http://localhost:" + port + "/api/users"))
                .andExpect(jsonPath("$", hasSize(1)));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void updateUserById() {

        long id = getIdCommitedUser();
        try{
            mockMvc.perform(MockMvcRequestBuilders
                .put("http://localhost:" + port + "/api/users/"+id)
                .content(testValues.testUserUpdate)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        }catch(Exception e){
            e.printStackTrace();
        }
        User userFromTable = component.findByIdOrDie(id);
        LocalDateTime time = LocalDateTime.now();
        assertNotNull(userFromTable);
        assertEquals(testValues.testUpdateName, userFromTable.getUsername());
        assertEquals(testValues.testUpdatePassword, userFromTable.getPassword());
        assertEquals(testValues.testUpdateEmail, userFromTable.getEmail());
        assertEquals(time.getHour(), userFromTable.getCreatedAt().getHour());
        assertEquals(time.getMinute(), userFromTable.getCreatedAt().getMinute());
        assertEquals(time.getHour(), userFromTable.getUpdatedAt().getHour());
        assertEquals(time.getMinute(), userFromTable.getUpdatedAt().getMinute());
        component.deleteUserById(id);
    }

    private long getIdCommitedUser() {
        MvcResult result = null;
        try{
            result = mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/users")
                            .content(DataTestValues.testUser)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn();
        }catch(Exception e){
            e.printStackTrace();
        }
        try {
            return  Long.valueOf(result.getResponse().getContentAsString().split(":")[1].split(",")[0]);
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return 0;
    }
}