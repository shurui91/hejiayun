package com.msb.springsecurity_example2.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.msb.springsecurity_example2.entity.CheckCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class KaptchaController {
    private final Producer producer;

    @Autowired
    public KaptchaController(Producer producer) {
        this.producer = producer;
    }

    @GetMapping("/code/image")
    public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.创建验证码文本
        String capText = producer.createText();

        //2.创建验证码图片
        BufferedImage bufferedImage = producer.createImage(capText);

        //3.将验证码文本放进 Session 中
        CheckCode code = new CheckCode(capText);
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, code);

        //4.将验证码图片返回，禁止验证码图片缓存
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        //5.设置ContentType，响应验证码
        response.setContentType("image/png");
        ImageIO.write(bufferedImage, "jpg", response.getOutputStream());
    }
}
