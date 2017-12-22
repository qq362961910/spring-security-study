package com.jy.study.spring.security.browser.config;

import com.jy.study.spring.security.browser.authentication.BrowserAuthenticationFailureHandler;
import com.jy.study.spring.security.browser.authentication.BrowserAuthenticationSuccessHandler;
import com.jy.study.spring.security.security.core.properties.SecurityProperties;
import com.jy.study.spring.security.security.core.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;

import javax.sql.DataSource;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private BrowserAuthenticationSuccessHandler browserAuthenticationSuccessHandler;
    @Autowired
    private BrowserAuthenticationFailureHandler browserAuthenticationFailureHandler;
    @Autowired
    private SessionStrategy sessionStrategy;
    @Autowired
    private AntPathMatcher antPathMatcher;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(browserAuthenticationFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.setSessionStrategy(sessionStrategy);
        validateCodeFilter.setAntPathMatcher(antPathMatcher);
        validateCodeFilter.afterPropertiesSet();
        http
            .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
            //表单配置,指定登录页面和登录提交地址
            .formLogin()
                .loginPage("/authentication/require")
                .loginProcessingUrl("/authentication/form")
                .successHandler(browserAuthenticationSuccessHandler)
                .failureHandler(browserAuthenticationFailureHandler)
            .and()
            .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
            .and()
            //请求配置,除了指定匹配的路径,拦截所有url登录验证
            .authorizeRequests()
                .antMatchers("/authentication/require", securityProperties.getBrowser().getLoginPage(), "/code/image")
                .permitAll()
                .anyRequest()
                .authenticated()
            .and()
            //csrf配置, 取消csrf拦截验证(默认开启)
            .csrf()
                .disable();
    }

    /**
     * 密码加密工具
     * */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    /**
     * 缓存被拦截的请求
     * */
    @Bean
    public RequestCache requestCache() {
        return new HttpSessionRequestCache();
    }

    /**
     * 重定向工具
     * */
    @Bean
    public RedirectStrategy redirectStrategy() {
        return new DefaultRedirectStrategy();
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
