package com.jy.study.spring.security.security.core.validate.code.image;

import com.jy.study.spring.security.security.core.validate.code.ValidateCode;

import java.awt.image.BufferedImage;

public class ImageCode extends ValidateCode {

    /**
     * 图片
     * */
    private BufferedImage image;


    public ImageCode(BufferedImage image, String code, int seconds) {
        super(code, seconds);
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

}
