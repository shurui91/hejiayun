package com.msb.hjycommunity.system.service;

import com.msb.hjycommunity.system.domain.SysMenu;
import com.msb.hjycommunity.system.domain.vo.RouterVo;

import java.util.List;
import java.util.Set;

/**
 * 菜单 业务层
 */
public interface SysMenuService {
    /**
     * 根据用户ID查询菜单信息
     * @param userId
     * @return: 角色权限列表
     */
    public Set<String> selectMenuPermissionByUserId(Long userId);

    /**
     * 根据用户ID 查询菜单树信息
     * @param userId
     * @return
     */
    public List<SysMenu> selectMenuTreeByUserId(Long userId);

    /**
     * 构建前端路由所需要的菜单
     * @param menus 菜单列表
     * @return: 路由列表
     */
    public List<RouterVo> buildMenus(List<SysMenu> menus);
}
