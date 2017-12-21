package com.jy.study.spring.security.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeGenerator {
    ImageCode createImageCode(ServletWebRequest servletWebRequest);
}
