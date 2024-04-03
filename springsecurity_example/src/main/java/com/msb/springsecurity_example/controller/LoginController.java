package com.msb.springsecurity_example.controller;

import com.msb.springsecurity_example.common.ResponseResult;
import com.msb.springsecurity_example.entity.LoginBody;
import com.msb.springsecurity_example.entity.SysUser;
import com.msb.springsecurity_example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

// @CrossOrigin(origins = "http://localhost:8080")
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    //@PostMapping("/user/login")
    public ResponseResult login(@RequestBody SysUser sysUser) {
        // 登录
        return loginService.login(sysUser);
    }

    /**
     * 用户登录 + 验证码
     * @param loginBody
     * @return
     */
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody LoginBody loginBody) {
        // 生成令牌
        String token = loginService.login(loginBody.getUserName(), loginBody.getPassword(), loginBody.getCode(), loginBody.getUuid());
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", token);
        return new ResponseResult(200, "登录成功", map);
    }

    /**
     * 用户登出
     * @return
     */
    @GetMapping("/user/logout")
    public ResponseResult logout() {
        return loginService.logout();
    }
}
