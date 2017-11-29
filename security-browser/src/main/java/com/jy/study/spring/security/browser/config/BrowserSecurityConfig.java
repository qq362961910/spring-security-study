package com.jy.study.spring.security.browser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
//        http.httpBasic()
            .and()
            .authorizeRequests()
            .anyRequest()
            .authenticated();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        String hash1 = BCrypt.hashpw("123456", BCrypt.gensalt());
        String hash2 = BCrypt.hashpw("111111", BCrypt.gensalt());
        System.out.println(hash1);
        System.out.println(hash2);
        System.out.println(BCrypt.checkpw("123456", hash2));
        System.out.println(BCrypt.checkpw("111111", hash2));
    }
}
