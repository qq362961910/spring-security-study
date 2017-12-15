package com.jy.study.spring.security.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Security 配置信息
 * */
@ConfigurationProperties(prefix = "application.security")
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }
}
