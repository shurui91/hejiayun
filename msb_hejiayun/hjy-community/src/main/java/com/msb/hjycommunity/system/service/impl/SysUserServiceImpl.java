package com.msb.hjycommunity.system.service.impl;

import com.msb.hjycommunity.system.domain.SysUser;
import com.msb.hjycommunity.system.mapper.SysUserMapper;
import com.msb.hjycommunity.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser selectUserByUserName(String userName) {
        return sysUserMapper.selectUserByUserName(userName);
    }
}
