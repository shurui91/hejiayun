package com.msb.hjycommunity.system.service.impl;

import com.msb.hjycommunity.common.constant.UserConstants;
import com.msb.hjycommunity.system.domain.SysMenu;
import com.msb.hjycommunity.system.domain.vo.MetaVo;
import com.msb.hjycommunity.system.domain.vo.RouterVo;
import com.msb.hjycommunity.system.mapper.SysMenuMapper;
import com.msb.hjycommunity.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public Set<String> selectMenuPermissionByUserId(Long userId) {
        List<String> list = sysMenuMapper.selectMenuPermissionByUserId(userId);
        HashSet<String> menuSet = new HashSet<>();
        for (String menuKey : list) {
            if (!StringUtils.isEmpty(menuKey)) {
                menuSet.add(menuKey);
            }
        }
        return menuSet;
    }

    @Override
    public List<SysMenu> selectMenuTreeByUserId(Long userId) {
        List<SysMenu> menus = null;
        // admin
        if (userId != null && userId == 1L) {
            menus = sysMenuMapper.selectMenuTreeAll();
        } else {
            menus = sysMenuMapper.selectMenuTreeByUserId(userId);
        }
        //todo 封装子菜单
        return getChildPerms(menus, 0);
    }

    /**
     * 根据父节点ID 获取所有子节点
     *
     * @param menus
     * @param parentId 传入的父节点Id
     * @return: java.util.List<com.msb.hjycommunity.system.domain.SysMenu>
     */
    private List<SysMenu> getChildPerms(List<SysMenu> menus, int parentId) {
        List<SysMenu> returnList = new ArrayList<>();
        menus.stream()
                .filter(m -> m.getParentId() == parentId)
                .forEach(m -> {
                    recursionFn(menus, m);
                    returnList.add(m);
                });
        return returnList;
    }

    /**
     * 递归获取子菜单
     *
     * @param menus
     * @param m
     */
    private void recursionFn(List<SysMenu> menus, SysMenu m) {
        //1. 得到子节点列表,保存到父菜单的children中
        List<SysMenu> childList = getChildList(menus, m);
        m.setChildren(childList);
        for (SysMenu childMenu : childList) {
            //判断子节点下是否还有子节点
            if (getChildList(menus, childMenu).size() > 0 ? true : false) {
                recursionFn(menus, childMenu);
            }
        }
    }

    /**
     * 得到子节点列表
     *
     * @param menus
     * @param m
     * @return: 子菜单集合
     */
    private List<SysMenu> getChildList(List<SysMenu> menus, SysMenu m) {
        List<SysMenu> subMenus = menus.stream()
                .filter(sub -> sub.getParentId().longValue() == m.getMenuId().longValue())
                .collect(Collectors.toList());
        return subMenus;
    }

    @Override
    public List<RouterVo> buildMenus(List<SysMenu> menus) {
        List<RouterVo> routers = new LinkedList<>();
        for (SysMenu menu : menus) {
            RouterVo routerVo = new RouterVo();
            //设置路由名称 例如: System 开头字母大写
            routerVo.setName(getRouteName(menu));
            //设置路由地址 例如: 根目录 /system , 二级目录 user
            routerVo.setPath(getRoutePath(menu));
            //设置组件地址 例如: system/user/index
            routerVo.setComponent(getComponent(menu));
            //设置是否隐藏 ,隐藏后侧边栏不会出现
            routerVo.setHidden("1".equals(menu.getVisible()));
            //基础元素
            routerVo.setMeta(new MetaVo(menu.getMenuName(),menu.getIcon(),"1".equals(menu.getIsCache())));
            //子菜单
            List<SysMenu> subMenuList = menu.getChildren();
            //子菜单不为空 && 类型为M 菜单类型（目录 顶级父菜单）
            if(!subMenuList.isEmpty() && subMenuList.size() > 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType())){
                routerVo.setAlwaysShow(true);   //下面有子路由
                routerVo.setRedirect("noRedirect"); //在导航栏中不可点击
                routerVo.setChildren(buildMenus(subMenuList)); //递归设置子菜单
            }
            routers.add(routerVo);
        }
        return routers;
    }

    /**
     * 首字母大写路由名称
     *
     * @param menu 菜单信息
     * @return: 路由名称
     */
    public String getRouteName(SysMenu menu) {
        String routerName = org.apache.commons.lang3.StringUtils.capitalize(menu.getPath());
        return routerName;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return: 路由地址
     */
    public String getRoutePath(SysMenu menu) {
        String path = menu.getPath();
        //非外链 并且是一级目录,菜单类型为 M(目录)
        if (menu.getParentId().intValue() == 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType())
                && UserConstants.NO_FRAME.equals(menu.getIsFrame())) {
            path = "/" + menu.getPath();
        }
        return path;
    }

    /**
     * 获取组件信息
     * @param menu
     * @return: 组件信息
     */
    public String getComponent(SysMenu menu) {
        String component = UserConstants.LAYOUT;
        //如果是子菜单
        if (!StringUtils.isEmpty(menu.getComponent())) {
            component = menu.getComponent();
        } else if (menu.getParentId().intValue() != 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType())) {
            component = UserConstants.PARENT_VIEW;
        }
        return component;
    }
}
