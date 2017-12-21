package com.jy.study.spring.security.security.core.properties;

/**
 * 浏览器配置信息
 * */
public class BrowserProperties {

    private String loginPage = "/my-login.html";

    private LoginType loginType = LoginType.JSON;

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
}
