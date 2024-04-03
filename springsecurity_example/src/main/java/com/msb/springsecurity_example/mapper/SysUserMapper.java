package com.msb.springsecurity_example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msb.springsecurity_example.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 通过用户名查询用户
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    public SysUser selectUserByUserName(String userName);
}
