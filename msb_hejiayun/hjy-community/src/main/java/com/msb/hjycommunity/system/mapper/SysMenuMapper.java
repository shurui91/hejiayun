package com.msb.hjycommunity.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msb.hjycommunity.system.domain.SysMenu;

import java.util.List;

/**
 * 菜单表数据层
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 根据用户id查询菜单权限
     * @param userId
     * @return: 权限列表
     */
    public List<String> selectMenuPermissionByUserId(Long userId);

    /**
     * 用户为admin时,查询全部菜单信息
     * @param
     * @return: 菜单列表
     */
    public List<SysMenu> selectMenuTreeAll();

    /**
     * 根据用户id 查询菜单信息
     * @param
     * @return: 菜单列表
     */
    public List<SysMenu> selectMenuTreeByUserId(Long userId);
}
