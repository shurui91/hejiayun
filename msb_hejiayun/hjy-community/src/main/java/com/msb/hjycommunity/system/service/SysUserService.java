package com.msb.hjycommunity.system.service;

import com.msb.hjycommunity.system.domain.SysUser;

public interface SysUserService {
    /**
     * 通过用户名查询用户
     * @param userName
     * @return: com.msb.hjycommunity.system.domain.SysUser
     */
    public SysUser selectUserByUserName(String userName);
}
