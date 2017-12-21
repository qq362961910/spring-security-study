package com.jy.study.spring.security.security.core.validate.code;


import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String explanation) {
        super(explanation);
    }
}
