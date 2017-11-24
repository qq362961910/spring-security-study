package com.jy.study.spring.security.controller;

import com.jy.study.spring.security.exception.UserNotException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(UserNotException.class)
    public Map<String, Object> handleUserNotExistException(UserNotException userNotException) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", userNotException.getUserId());
        result.put("message", userNotException.getMessage());
        return result;
    }
}
