package org.example.userstask.services;

import org.example.userstask.entity.User;
import org.example.userstask.web.model.request.UserRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration
public class UpdateUserTest {
    @Autowired
    private DataTestValues testValues;

    @Autowired
    private UserComponent component;
    static long id;

    @BeforeEach
    void setUp(){
        UserRequest request = new UserRequest();
        request.setPassword(testValues.testPassword);
        request.setUsername(testValues.testName);
        request.setEmail(testValues.testEmail);
        try {
            User user = component.commit(request);
            id = user.getId();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void updateUserById() {

        UserRequest requestUpdate = new UserRequest();
        requestUpdate.setEmail(testValues.testUpdateEmail);
        requestUpdate.setPassword(testValues.testUpdatePassword);
        requestUpdate.setUsername(testValues.testUpdateName);

        component.updateUserById(id,requestUpdate);
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
    }

    @AfterEach
    void deleteUserById(){
        try {
            component.deleteUserById(id);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
