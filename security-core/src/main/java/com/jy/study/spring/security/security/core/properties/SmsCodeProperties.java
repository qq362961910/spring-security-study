package com.jy.study.spring.security.security.core.properties;

public class SmsCodeProperties {

    /**
     * 长度
     * */
    private int length = 4;

    /**
     * 超时时间
     * */
    private int expireInSeconds = 60;

    /**
     * 需要拦截的url
     * */
    private String url;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpireInSeconds() {
        return expireInSeconds;
    }

    public void setExpireInSeconds(int expireInSeconds) {
        this.expireInSeconds = expireInSeconds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
