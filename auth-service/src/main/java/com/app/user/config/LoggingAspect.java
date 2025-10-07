package com.app.user.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.app.user.controller.AuthController.login(..)) && args(loginRequest)")
    public void loginPointCut(Object loginRequest) {
    }

    @Before("loginPointCut(loginRequest)")
    public void printLoginRequest(Object loginRequest) {
        log.info(loginRequest.toString());
    }

}
