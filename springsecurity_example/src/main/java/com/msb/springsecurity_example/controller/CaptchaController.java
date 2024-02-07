package com.msb.springsecurity_example.controller;

import com.msb.springsecurity_example.common.Constants;
import com.msb.springsecurity_example.common.ResponseResult;
import com.msb.springsecurity_example.utils.RedisCache;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class CaptchaController {
    @Autowired
    private RedisCache redisCache;

    /**
     * 生成验证码
     *
     * @param response
     * @return: com.mashibing.springsecurity_example.common.ResponseResult
     */
    @GetMapping("/captchaImage")
    public ResponseResult getCode(HttpServletResponse response) {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);

        //生成验证码,及验证码唯一标识
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String key = Constants.CAPTCHA_CODE_KEY + uuid;
        String code = specCaptcha.text().toLowerCase();

        //保存到redis
        redisCache.setCacheObject(key, code, 1000, TimeUnit.SECONDS);

        //创建map
        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid", uuid);
        map.put("img", specCaptcha.toBase64());
        return new ResponseResult(200, "验证码获取成功", map);
    }
}
