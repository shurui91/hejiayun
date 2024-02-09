package com.msb.springsecurity_example.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用于获取用户被授予的权限
 */
@Data
public class LoginUser implements UserDetails {
    private SysUser sysUser;
    // 存储权限的集合
    private List<String> permissions;
    // 存储角色信息的集合
    private List<String> roles;
    // authorities权限集合，无需序列化
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;

    public LoginUser() {
    }

    public LoginUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public LoginUser(SysUser sysUser, List<String> permissions) {
        this.sysUser = sysUser;
        this.permissions = permissions;
    }

    public LoginUser(SysUser sysUser, List<String> permissions, List<String> roles) {
        this.sysUser = sysUser;
        this.permissions = permissions;
        this.roles = roles;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    /**
     * 用于获取用户被授予的权限，可以用于实现访问控制
     *
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 将permissions集合中的String类型权限信息,转换为SimpleGrantedAuthority类型
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        for (String permission : permissions) {
//            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permission);
//            authorities.add(simpleGrantedAuthority);
//        }
        if (authorities != null) {
            return authorities;
        }
        //转换权限信息 1.8 语法完成
        authorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        //转换角色信息 1.8 语法完成
        authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
        //authorities ['ROLE_admin', 'system:user:list']
        return authorities;
    }

    /**
     * 获取用户密码，进行密码验证
     *
     * @return
     */
    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    /**
     * 获取用户名
     *
     * @return
     */
    @Override
    public String getUsername() {
        return sysUser.getUserName();
    }

    /**
     * 用于判断用户的账户是否过期
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 用于判断用户的账户是否被锁定
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用于判断用户的凭证是否过期
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用于判断用户是否已经激活
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
