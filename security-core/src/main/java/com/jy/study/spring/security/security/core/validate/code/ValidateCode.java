package com.jy.study.spring.security.security.core.validate.code;

import java.time.LocalDateTime;

/**
 * 短信验证码
 * */
public class ValidateCode {

    /**
     * 验证码
     * */
    private String code;

    /**
     * 过期时间
     * */
    private LocalDateTime expireTime;

    public ValidateCode(String code, int seconds) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(seconds);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public boolean isExpired() {
        return expireTime.isBefore(LocalDateTime.now());
    }
}
