package com.yj.study.spring.security.exception;

public class UserNotException extends RuntimeException {

    private Long userId;

    public UserNotException(Long userId) {
        super("user not exist");
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
