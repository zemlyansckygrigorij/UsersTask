package org.example.userstask.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.example.userstask.web.model.response.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class WebLogger
 */
@Slf4j
@Aspect
@Component
public class WebLogger {
    Logger logger = LoggerFactory.getLogger(WebLogger.class);

    @Pointcut("execution(public * org.example.userstask.web.controller.UserController.*(..))")
    public void callAtUserController() { }

    @Pointcut("execution(public * org.example.userstask.web.controller.UserController.findByIdOrDie(..))")
    public void findByIdOrDie() { }

    @Pointcut("execution(public * org.example.userstask.web.controller.UserController.commit(..))")
    public void commit() { }

    @Pointcut("execution(public * org.example.userstask.web.controller.UserController.findAll())")
    public void findAll() { }

    @Before("callAtUserController()")
    public void beforeCallAtMethod(JoinPoint jp) {
        LocalDateTime time = LocalDateTime.now();
        String args = "";

        if(jp.getArgs().length>0){
            args = " insert argument "+ Arrays.toString(jp.getArgs());
        }
        logger.info(jp.toShortString() +  args +" time-" +time.getHour()+":"+time.getMinute());
    }


    @AfterReturning(
            pointcut = "findByIdOrDie()",
            returning = "result")
    public void findByIdOrDie(UserResponse result) {
        logger.info("findUserById - "+result.toString());
    }
    @AfterReturning(
            pointcut = "commit()",
            returning = "result")
    public void commit(UserResponse result) {
        logger.info("commit - "+result.toString());
    }

    @AfterReturning(
            pointcut = "findAll()",
            returning = "result")
    public void findAll(List<UserResponse> result) {
        logger.info("findAll - "+result.toString());
    }
}
