package com.msb.hjycommunity.system.service.impl;

import com.msb.hjycommunity.system.mapper.SysRoleMapper;
import com.msb.hjycommunity.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<String> list = sysRoleMapper.selectRolePermissionByUserId(userId);
        HashSet<String> permSet = new HashSet<>();
        for (String roleKey : list) {
            if (!StringUtils.isEmpty(roleKey)) {
                permSet.add(roleKey);
            }
        }
        return permSet;
    }
}
