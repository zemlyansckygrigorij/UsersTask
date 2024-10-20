package org.example.userstask.services;

import org.example.userstask.entity.User;
import org.example.userstask.web.model.request.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import java.time.LocalDateTime;
import java.util.OptionalLong;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ContextConfiguration

class UserComponentImplTest {
    @Autowired
    private UserComponent component;
    @Autowired
    private DataTestValues testValues;


    @Test
    void notNull(){
        assertNotNull(testValues);
    }

    @Test
    void findByIdOrDie() {
        User userFromTable = component.findByIdOrDie(1L);
        assertNotNull(userFromTable);
        assertEquals(testValues.testNameNew, userFromTable.getUsername());
        assertEquals(testValues.testPassword, userFromTable.getPassword());
        assertEquals(testValues.testEmailNew, userFromTable.getEmail());
        assertEquals(22, userFromTable.getCreatedAt().getHour());
        assertEquals(54, userFromTable.getCreatedAt().getMinute());
        assertEquals(22, userFromTable.getUpdatedAt().getHour());
        assertEquals(54, userFromTable.getUpdatedAt().getMinute());
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
    void findAll(){
        assertEquals(1, component.findAll().size());
    }

    @Test
    void deleteUserById() {
        long id = getIdCommitedUser();
        assertEquals(2, component.findAll().size());
        component.deleteUserById(id);
        assertEquals(1, component.findAll().size());
    }

    @Test
    void updateUserById() {
        long id = getIdCommitedUser();
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
        component.deleteUserById(id);
    }

    private long getIdCommitedUser(){
        User user = null;
        try{
            user = component.commit(new UserRequest(testValues.testName,testValues.testEmail,testValues.testPassword ));
            return user.getId();
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}