package com.msb.springsecurity_example.controller;

import com.msb.springsecurity_example.common.ResponseResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloSecurityController {
    /**
     * 设置资源访问所需要的权限
     *
     * @PreAuthorize() 在方法执行前进行权限校验
     * hasAuthority 检查用户是否具有指定的权限
     * hasAuthority('test') 检查用户是否具有test的权限
     * 在方法执行之后进行权限校验 @PostAuthorize()
     */
    //拥有system:user:list权限才能访问
    @RequestMapping("/hello")
    @PreAuthorize("hasAuthority('system:user:list')")
    public String hello() {
        return "Hello from Spring Security /hello!";
    }

    //拥有system:role:list 才能访问
    @RequestMapping("/ok")
    @PreAuthorize("hasAnyAuthority('admin', 'system:role:list', 'system:menu:list')")   // 检查调用者是否具有指定权限
    public String ok() {
        return "Hello from Spring Security /ok!";
    }

    @RequestMapping("/testCors")
    public ResponseResult testCors() {
        return new ResponseResult(200, "Hello from Spring Security /testCors!");
        //return "Hello from Spring Security /testCors!";
    }

    @RequestMapping("/level1")
    //当前用户是common角色,并且具有system:role:list或者system:user:list
    @PreAuthorize("hasRole('admin') AND hasAnyAuthority('system:role:list','system:user:list')")
    public String level1() {
        return "level1 page";
    }

    @RequestMapping("/level2")
    //当前用户拥有admin或者common角色,或者具有system:role:list权限
    @PreAuthorize("hasAnyRole('admin','common') OR hasAuthority('system:role:list')")
    public String level2() {
        return "level2 page";
    }

    @RequestMapping("/yes")
    // 这个配置还可以在SecurityConfig.java里做
    // 配置形式的权限控制
    @PreAuthorize("@my_ex.hasAuthority('system:role:list')")
    public String yes() {
        return "Hello from Spring Security /yes!";
    }
}
