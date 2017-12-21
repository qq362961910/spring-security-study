package com.jy.study.spring.security.security.core.validate.code;

import com.jy.study.spring.security.security.core.properties.SecurityProperties;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ImageCodeGenerator implements ValidateCodeGenerator {

    private SecurityProperties securityProperties;

    @Override
    public ImageCode createImageCode(ServletWebRequest servletWebRequest) {
        int width = ServletRequestUtils.getIntParameter(servletWebRequest.getRequest(), "width", securityProperties.getCode().getImage().getWidth());
        int height = ServletRequestUtils.getIntParameter(servletWebRequest.getRequest(), "height", securityProperties.getCode().getImage().getHeight());
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Random random = new Random();
        g.setColor(getRandomColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.setColor(getRandomColor(160, 200));
        for(int i=0; i<155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x+xl, y+yl);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<securityProperties.getCode().getImage().getLength(); i++) {
            stringBuilder.append(random.nextInt(10));
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(String.valueOf(stringBuilder.charAt(i)), 13 * i +6, 16);
        }
        g.dispose();
        return new ImageCode(image, stringBuilder.toString(), securityProperties.getCode().getImage().getExpireInSeconds());
    }
    /**
     * 生成背景随机条纹
     * */
    private Color getRandomColor(int fc, int bc) {
        Random random = new Random();
        if(fc > 255) {
            fc = fc % 255;
        }
        if(bc > 255) {
            bc = bc % 255;
        }
        int r = fc + random.nextInt(bc -fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
