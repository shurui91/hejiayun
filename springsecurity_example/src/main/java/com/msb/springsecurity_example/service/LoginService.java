package com.msb.springsecurity_example.service;

import com.msb.springsecurity_example.common.ResponseResult;
import com.msb.springsecurity_example.entity.SysUser;

public interface LoginService {
    ResponseResult login(SysUser sysUser);

    ResponseResult logout();

    String login(String username, String password, String code, String uuid);
}
