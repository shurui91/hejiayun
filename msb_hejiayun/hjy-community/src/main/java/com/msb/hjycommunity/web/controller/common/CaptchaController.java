package com.msb.hjycommunity.web.controller.common;

import com.msb.hjycommunity.common.constant.Constants;
import com.msb.hjycommunity.common.utils.UUIDUtils;
import com.msb.hjycommunity.common.utils.ChainedMap;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

/**
 * 获取验证码
 */
@RestController
public class CaptchaController {
    //当Redis当做数据库或者消息队列来操作时，我们一般使用RedisTemplate来操作
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 生成验证码
     *
     * @param response
     * @return: com.mashibing.springsecurity_example.common.ResponseResult
     */
    @GetMapping("/captchaImage")
    public ChainedMap getCode(HttpServletResponse response) {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);

        //生成验证码,及验证码唯一标识
        String uuid = UUIDUtils.simpleUUID();
        String key = Constants.CAPTCHA_CODE_KEY + uuid;
        String code = specCaptcha.text().toLowerCase();

        //保存到redis
        redisTemplate.opsForValue().set(key, code, Duration.ofMinutes(10));
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);
        return ChainedMap.create().set("timestamp", dateStr).set("uuid", uuid).set(
                "img",
                specCaptcha.toBase64());
    }
}
