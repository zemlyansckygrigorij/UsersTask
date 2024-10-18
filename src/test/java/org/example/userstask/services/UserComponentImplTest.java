package org.example.userstask.services;

import org.example.userstask.entity.User;
import org.example.userstask.web.model.request.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.OptionalLong;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ContextConfiguration
@Transactional
class UserComponentImplTest {
    @Autowired
    private UserComponent component;
    @Autowired
    private DataTestValues testValues;
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
    void notNull(){
        assertNotNull(testValues);
    }

    @Test
    void findByIdOrDie() {
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
    }

    @Test
    void findByIdOrDieThrowException() {
        OptionalLong optLong = component.findAll().stream().mapToLong(u->u.getId()).max();
        if(optLong.isPresent()) {
            long id = optLong.getAsLong();
            Exception exception = assertThrows(Exception.class, () ->
                    component.findByIdOrDie(id + 1));

            assertEquals("user with this id not found !", exception.getMessage());
        }
    }



    @Test
    void findAll(){
        assertEquals(1, component.findAll().size());
    }

    @Test
    void deleteUserById() {
        assertEquals(1, component.findAll().size());
        try{
            component.deleteUserById(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals(0, component.findAll().size());
    }
}