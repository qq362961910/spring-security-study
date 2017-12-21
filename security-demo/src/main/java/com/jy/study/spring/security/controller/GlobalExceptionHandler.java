package com.jy.study.spring.security.controller;

import com.jy.study.spring.security.exception.UserNotException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserNotException.class)
    @ResponseBody
    public Map<String, Object> handleUserNotExistException(UserNotException userNotException) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", userNotException.getUserId());
        result.put("message", userNotException.getMessage());
        return result;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public Map<String, Object> noHandlerFoundException(HttpServletRequest request, NoHandlerFoundException noHandlerFoundException) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "未找到处理器");
        return result;
    }

}
