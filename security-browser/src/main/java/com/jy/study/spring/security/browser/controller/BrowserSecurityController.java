package com.jy.study.spring.security.browser.controller;

import com.jy.study.spring.security.security.core.properties.SecurityProperties;
import com.jy.study.spring.security.browser.support.SimpleResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class BrowserSecurityController {

    private static final Logger logger = LoggerFactory.getLogger(BrowserSecurityController.class);

    private RequestCache requestCache;
    private RedirectStrategy redirectStrategy;

    public BrowserSecurityController(RequestCache requestCache, RedirectStrategy redirectStrategy) {
        this.requestCache = requestCache;
        this.redirectStrategy = redirectStrategy;
    }

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 当需要身份认证时跳转到这里
     * 根据被拦截的url判断是json响应还是进行页面跳转
     * */
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @RequestMapping("/authentication/require")
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            logger.info("引发跳转的请求是: {}", targetUrl);
            if(StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
            }
        }
        return new SimpleResponse("访问的服务需要身份认证, 请引导用户到登录页");
    }
}
