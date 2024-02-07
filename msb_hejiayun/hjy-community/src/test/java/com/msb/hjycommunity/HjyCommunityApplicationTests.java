package com.msb.hjycommunity;

import com.msb.hjycommunity.system.domain.SysMenu;
import com.msb.hjycommunity.system.domain.SysUser;
import com.msb.hjycommunity.system.mapper.SysMenuMapper;
import com.msb.hjycommunity.system.mapper.SysUserMapper;
import com.msb.hjycommunity.system.service.SysMenuService;
import com.msb.hjycommunity.system.service.SysRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

@SpringBootTest
class HjyCommunityApplicationTests {
    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysMenuService sysMenuService;

    @Autowired
    SysMenuMapper sysMenuMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void testSelectUserByUserName() {
        SysUser admin = sysUserMapper.selectUserByUserName("admin");
        System.out.println(admin);
    }

    @Test
    public void testSelectRoleAndMenuByUserId() {
        Set<String> s1 = sysRoleService.selectRolePermissionByUserId(2L);
        System.out.println("用户角色权限信息" + s1);
        Set<String> s2 = sysMenuService.selectMenuPermissionByUserId(2L);
        System.out.println("用户菜单权限信息" + s2);
    }

    @Test
    public void testSelectMenuTreeAll() {
//        List<SysMenu> list = sysMenuMapper.selectMenuTreeAll();
//        System.out.println(list);

//        List<SysMenu> menu1 = sysMenuMapper.selectMenuTreeByUserId(2L);
//        System.out.println(menu1);

        List<SysMenu> menu1 = sysMenuService.selectMenuTreeByUserId(2L);
        System.out.println(menu1);
    }
}
