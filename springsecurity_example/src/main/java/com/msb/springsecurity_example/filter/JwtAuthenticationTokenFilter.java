package com.msb.springsecurity_example.filter;

import com.msb.springsecurity_example.entity.LoginUser;
import com.msb.springsecurity_example.utils.JwtUtil;
import com.msb.springsecurity_example.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 自定义认证过滤器，用来校验用户请求中携带token
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    RedisCache redisCache;

    /**
     * 封装过滤器的执行逻辑
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1.从请求头中获取token
        String token = request.getHeader("token");

        // 2.判断token是否为空,为空直接放行
        if (!StringUtils.hasText(token)) {
            // 放行
            filterChain.doFilter(request, response);
            // return的作用是返回响应的时候,避免走下面的逻辑
            return;
        }

        // 3.解析Token
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("非法token");
        }

        // 4.从redis中获取用户信息
        String redisKey = "login:" + userId;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("用户未登录");
        }

        // 5.将用户新保存到SecurityContextHolder,以便后续的访问控制和授权操作使用
        // todo 获取权限信息封装到 Authentication
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 6.放行
        filterChain.doFilter(request, response);
    }
}
