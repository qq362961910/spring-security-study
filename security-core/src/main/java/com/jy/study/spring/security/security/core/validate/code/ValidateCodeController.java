package com.jy.study.spring.security.security.core.validate.code;

import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("code")
@RestController
public class ValidateCodeController {

    public static final String IMAGE_CODE_KEY = "IMAGE_CODE_KEY";


    private SessionStrategy sessionStrategy;
    private ValidateCodeGenerator imageCodeGenerator;

    public ValidateCodeController(SessionStrategy sessionStrategy, ValidateCodeGenerator imageCodeGenerator) {
        this.sessionStrategy = sessionStrategy;
        this.imageCodeGenerator = imageCodeGenerator;
    }

    @GetMapping("image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = imageCodeGenerator.createImageCode(new ServletWebRequest(request));
        sessionStrategy.setAttribute(new ServletWebRequest(request), IMAGE_CODE_KEY, imageCode);
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }
}
