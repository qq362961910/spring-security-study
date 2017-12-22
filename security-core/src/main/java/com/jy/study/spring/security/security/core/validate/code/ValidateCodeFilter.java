package com.jy.study.spring.security.security.core.validate.code;

import com.jy.study.spring.security.security.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * OncePerRequestFilter保证每个请求只会过滤一次
 * */
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private AuthenticationFailureHandler authenticationFailureHandler;
    private SessionStrategy sessionStrategy;
    private SecurityProperties securityProperties;
    private AntPathMatcher antPathMatcher;
    private Set<String> urls = new HashSet<>();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(), ",");
        if(configUrls != null) {
            urls.addAll(Arrays.asList(configUrls));
        }
        urls.add("/authentication/form");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean action = false;
        for(String url: urls) {
            if(antPathMatcher.match(url, request.getRequestURI())) {
                action = true;
                break;
            }
        }
        if(action) {
            try {
                validate(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
        ImageCode imageCode = (ImageCode)sessionStrategy.getAttribute(servletWebRequest, ValidateCodeController.IMAGE_CODE_KEY);
        String code = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "code");
        if(StringUtils.isBlank(code)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        if(imageCode == null) {
            throw new ValidateCodeException("验证码不存在");
        }
        if(imageCode.isExpired()) {
            sessionStrategy.removeAttribute(servletWebRequest, ValidateCodeController.IMAGE_CODE_KEY);
            throw new ValidateCodeException("验证码已过期");
        }
        if(!StringUtils.equals(imageCode.getCode(), code)) {
            throw new ValidateCodeException("验证码不匹配");
        }
        sessionStrategy.removeAttribute(servletWebRequest, ValidateCodeController.IMAGE_CODE_KEY);
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public SessionStrategy getSessionStrategy() {
        return sessionStrategy;
    }

    public void setSessionStrategy(SessionStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    public AntPathMatcher getAntPathMatcher() {
        return antPathMatcher;
    }

    public void setAntPathMatcher(AntPathMatcher antPathMatcher) {
        this.antPathMatcher = antPathMatcher;
    }
}
