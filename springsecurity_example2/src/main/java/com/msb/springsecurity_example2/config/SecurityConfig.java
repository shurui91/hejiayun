package com.msb.springsecurity_example2.config;

import com.msb.springsecurity_example2.filter.KaptchaFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * 启用全局方法级别的安全控制
 * 设置prePostEnabled = true，pre表示在方法执行前进行授权校验，post表示在方法执行后进行授权校验
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private KaptchaFilter kaptchaFilter;

    /**
     * 用于配置HTTP请求的安全处理
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //开始配置授权，即允许哪些请求访问系统
        http.authorizeHttpRequests()
                .mvcMatchers("/login.html", "/code/image").permitAll()   //指定哪些请求路径允许访问
                // .mvcMatchers("/index").permitAll()      //指定哪些请求路径允许访问
                .anyRequest().authenticated();  //除上述以外,指定其他所有请求都需要经过身份验证

        //开启表单认证
        http.formLogin()
                .loginPage("/login.html")      //登录页面
                .loginProcessingUrl("/login")  //提交路径
                .usernameParameter("username") //表单中用户名
                .passwordParameter("password") //表单中密码
                .successForwardUrl("/index")  //指定登录成功后要跳转的路径为 /index
                //.defaultSuccessUrl("/index")   //redirect 重定向  注意:如果之前请求路径,会有优先跳转之前请求路径
                //.failureUrl("/login.html") //指定登录失败后要跳转的路径为 /login.htm
                .successHandler(successHandler) // 认证成功处理器
                .failureHandler(failureHandler); // 认证失败处理器

        http.logout() // 开启注销配置
                //.logoutUrl("/logout") // 退出登录地址
                .invalidateHttpSession(true) // 退出时session是否失效，默认true
                .clearAuthentication(true) // 退出时是否清除认证信息，默认true
                .logoutSuccessHandler(logoutSuccessHandler);
                //.logoutSuccessUrl("/login.html") // 退出登录时，跳转的地址

        http.csrf().disable();//这里先关闭 CSRF

        //将自定义图形验证码校验过滤器,添加到UsernamePasswordAuthenticationFilter之前
        http.addFilterBefore(kaptchaFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
