package com.msb.springsecurity_example2.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CheckCode implements Serializable {
    private String code; //验证字符
    private LocalDateTime expireTime; //过期时间

    public CheckCode(String code, int expireTime) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireTime);
    }

    public CheckCode(String code) {
        // 默认验证码60秒过期
        this(code, 60);
    }

    // 是否过期
    public boolean isExpired() {
        return this.expireTime.isBefore(LocalDateTime.now());
    }

    public String getCode() {
        return code;
    }
}
