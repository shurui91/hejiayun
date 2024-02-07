package com.msb.hjycommunity.system.service;

import java.util.Set;

/**
 * 角色 业务层
 */
public interface SysRoleService {
    /**
     * 根据用户ID查询角色信息
     * @param userId
     * @return: 角色权限列表
     */
    public Set<String> selectRolePermissionByUserId(Long userId);
}
