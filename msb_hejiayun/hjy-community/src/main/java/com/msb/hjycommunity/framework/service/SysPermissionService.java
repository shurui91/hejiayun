package com.msb.hjycommunity.framework.service;

import com.msb.hjycommunity.system.domain.SysUser;
import com.msb.hjycommunity.system.service.SysMenuService;
import com.msb.hjycommunity.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户权限处理
 */
@Component
public class SysPermissionService {

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysMenuService menuService;

    /**
     * 获取角色数据权限
     * @param user
     * @return: 角色权限信息
     */
    public Set<String> getRolePermission(SysUser user) {
        Set<String> roles = new HashSet<>();
        //管理员拥有所有权限
        if (user.isAdmin()) {
            roles.add("admin");
        } else {
            roles = roleService.selectRolePermissionByUserId(user.getUserId());
        }
        return roles;
    }

    /**
     * 获取菜单数据权限
     * @param user
     * @return: java.util.Set<java.lang.String>
     */
    public Set<String> getMenuPermission(SysUser user) {
        Set<String> perms = new HashSet<>();
        //管理员拥有所有权限
        if (user.isAdmin()) {
            perms.add("*:*:*");
        } else {
            perms = menuService.selectMenuPermissionByUserId(user.getUserId());
        }
        return perms;
    }
}