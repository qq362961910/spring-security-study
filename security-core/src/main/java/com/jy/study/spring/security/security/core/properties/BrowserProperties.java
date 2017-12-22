package com.jy.study.spring.security.security.core.properties;

/**
 * 浏览器配置信息
 * */
public class BrowserProperties {

    /**
     * 登录页面
     * */
    private String loginPage = "/my-login.html";

    /**
     * 登录类型
     * */
    private LoginType loginType = LoginType.JSON;

    /**
     * 记住我时长
     * */
    private int rememberMeSeconds = 3600;

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }
}
