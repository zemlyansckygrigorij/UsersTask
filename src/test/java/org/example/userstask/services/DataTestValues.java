package org.example.userstask.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@Component
@ContextConfiguration
@PropertySource(
        "classpath:testValues.properties")
public class DataTestValues {
    @Value("${test.name}")
    public String testName;
    @Value("${test.email}")
    public String testEmail;
    @Value("${test.pasword}")
    public String testPassword;

    @Value("${test.update.name}")
    public String testUpdateName;
    @Value("${test.update.email}")
    public String testUpdateEmail;
    @Value("${test.update.pasword}")
    public String testUpdatePassword;

    @Value("${test.name.new}")
    public String testNameNew;
    @Value("${test.email.new}")
    public String testEmailNew;
    @Value("${test.created}")
    public String testCreatedAt;
    @Value("${test.updated}")
    public String testUpdatedAt;

    public static final String testUser = """
           {
               "username":"Test",
               "email":"test@test.com",
               "password":"testPassword"
           }
    """;

    public static final String testUserUpdate = """
           {
               "username":"Testupdate",
               "email":"testupdate@test.com",
               "password":"testPasswordUpdate"
           }
    """;
}
