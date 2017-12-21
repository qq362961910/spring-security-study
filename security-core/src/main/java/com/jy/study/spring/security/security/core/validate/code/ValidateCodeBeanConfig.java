package com.jy.study.spring.security.security.core.validate.code;

import com.jy.study.spring.security.security.core.properties.SecurityProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateCodeBeanConfig {

    private SecurityProperties securityProperties;

    public ValidateCodeBeanConfig(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    /**
     * spring在执行imageCodeGenerator()创建bean时首先检查spring容器内是否存在name为imageCodeGenerator的bean,
     * 如果有就不在执行方法imageCodeGenerator()创建bean
     * */
    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator() {
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }
}
