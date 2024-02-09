package com.msb.springsecurity_example.config;

import com.msb.springsecurity_example.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 启用全局方法级别的安全控制
 * 设置资源访问所需要的权限
 * 设置prePostEnabled = true，pre表示在方法执行前进行授权校验，post表示在方法执行后进行授权校验
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    // 未经身份验证的用户访问失败的filter
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    // 用户被拒绝访问时的filter
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    // BCryptPasswordEncoder注入到spring容器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 注入AuthenticationManager，供外部类使用
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 用于配置HTTP请求的安全处理
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭csrf
        http.csrf().disable();
        // 允许跨域
        http.cors();
        http
                // 不会创建会话，每个请求都将被视为独立的，无状态的STATELESS请求
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 定义请求授权规则
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .mvcMatchers("/user/login", "/captchaImage").anonymous()
                // 配置形式的权限控制
                .antMatchers("/yes").hasAuthority("system:menu:list")
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        // 将自定义认证过滤器添加到过滤器链
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //配置异常处理器
        http.exceptionHandling()
                //配置认证失败处理器 401
                .authenticationEntryPoint(authenticationEntryPoint)
                //配置授权失败处理器 403
                .accessDeniedHandler(accessDeniedHandler);

    }
}
