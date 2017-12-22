package com.jy.study.spring.security.code;

import com.jy.study.spring.security.security.core.validate.code.image.ImageCode;
import com.jy.study.spring.security.security.core.validate.code.ValidateCodeGenerator;
import org.springframework.web.context.request.ServletWebRequest;

//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator{

    @Override
    public ImageCode generate(ServletWebRequest servletWebRequest) {
        System.out.println("更高级的图形验证码生成器逻辑");
        return null;
    }
}
