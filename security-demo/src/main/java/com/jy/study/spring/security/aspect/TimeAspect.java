package com.jy.study.spring.security.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
//@Component
public class TimeAspect {

    @Around("execution(* com.jy.study.spring.security.controller.*.*(..))")
    public Object handlerControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("time aspect start");
        Object[] args = pjp.getArgs();
        for (Object arg: args) {
            System.out.println("arg is: " + arg);
        }
        Object object = pjp.proceed();
        System.out.println("time aspect end");
        return object;
    }

}
