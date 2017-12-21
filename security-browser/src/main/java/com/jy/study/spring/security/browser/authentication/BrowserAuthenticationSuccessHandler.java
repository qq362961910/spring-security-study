package com.jy.study.spring.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jy.study.spring.security.security.core.properties.LoginType;
import com.jy.study.spring.security.security.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录成功处理器
 * SavedRequestAwareAuthenticationSuccessHandler为spring boot 默认处理器
 */
@Component
public class BrowserAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(BrowserAuthenticationSuccessHandler.class);

    private ObjectMapper objectMapper;
    private SecurityProperties securityProperties;

    public BrowserAuthenticationSuccessHandler(ObjectMapper objectMapper, SecurityProperties securityProperties) {
        this.objectMapper = objectMapper;
        this.securityProperties = securityProperties;
    }

    /**
     * @param authentication 封装认证信息: 认证请求信息, 认证通过以后返回UserDetails等
     * */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("登录成功");
        if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        }
        else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
