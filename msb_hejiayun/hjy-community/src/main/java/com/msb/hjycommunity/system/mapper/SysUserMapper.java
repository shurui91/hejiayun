package com.msb.hjycommunity.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msb.hjycommunity.system.domain.SysUser;

public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 通过用户名查询用户
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserName(String userName);
}
