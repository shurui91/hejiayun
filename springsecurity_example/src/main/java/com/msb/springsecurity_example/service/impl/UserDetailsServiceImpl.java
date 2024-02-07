package com.msb.springsecurity_example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.msb.springsecurity_example.entity.LoginUser;
import com.msb.springsecurity_example.entity.SysUser;
import com.msb.springsecurity_example.mapper.MenuMapper;
import com.msb.springsecurity_example.mapper.RoleMapper;
import com.msb.springsecurity_example.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 实现从数据库根据用户名检索用户信息
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户信息
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUserName, username);
        SysUser sysUser = sysUserMapper.selectOne(wrapper);

        // 如果查询不到数据，抛出异常，给出提示
        if (Objects.isNull(sysUser)) {
            throw new RuntimeException("用户名或密码错误");
        }

        // todo 根据用户查询权限信息,添加到LoginUser中,这里的权限信息我们写死,封装到list集合
        // List<String> list = new ArrayList<>(Arrays.asList("test"));
        // return new LoginUser(sysUser, list);

        // 从数据库查询用户权限信息，保存到 LoginUser
        List<String> perms = menuMapper.selectPermsByUserId(sysUser.getUserId());

        // todo 获取当前用户的角色信息
        List<String> roles = roleMapper.selectRolesByUserId(sysUser.getUserId());

        // 方法的返回值是UserDetails类型，需要返回自定义的实现类
        return new LoginUser(sysUser, perms, roles);
    }
}
