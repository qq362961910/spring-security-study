package com.jy.study.spring.security.security.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;

@Configuration
public class CoreConfig {

    @Bean
    public SessionStrategy sessionStrategy() {
        return new HttpSessionSessionStrategy();
    }

    @Bean
    public AntPathMatcher antPathMatcher() {
        return new AntPathMatcher();
    }
}
