package com.msb.springsecurity_example;

import com.msb.springsecurity_example.entity.SysUser;
import com.msb.springsecurity_example.mapper.RoleMapper;
import com.msb.springsecurity_example.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
public class MapperTest {
    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testSelect() {
        List<SysUser> sysUsers = userMapper.selectList(null);
        System.out.println(sysUsers);
    }

    @Test
    public void testBcryp() {
        // 对原始密码进行加密
        String e1 = passwordEncoder.encode("password1");
        System.out.println(e1); // $2a$10$ZqMVu9gGNNySzYcQk8PtbOO03US33/.ukfzW7SJ3ZM66y25BqLAvS
        String e2 = passwordEncoder.encode("password1");
        // 这里会返回false，是因为用了随机盐值进行加密，所以每次加密结果不一致
        System.out.println(e1.equals(e2));

        // 对比原始密码和加密后的密码
        boolean b = passwordEncoder.matches("123456", "$2a$10$ZqMVu9gGNNySzYcQk8PtbOO03US33/.ukfzW7SJ3ZM66y25BqLAvS");
        System.out.println("==========" + b);
    }

    @Test
    public void testRoleSelect() {
        List<String> roles = roleMapper.selectRolesByUserId(1L);
        System.out.println(roles);
    }
}
