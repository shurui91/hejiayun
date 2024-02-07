package com.msb.springsecurity_example2.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 自定义认证失败处理器
 */
@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "登录失败: " + exception.getMessage());
        map.put("status", 500);
        response.setContentType("application/json;charset=UTF-8");
        String str = new ObjectMapper().writeValueAsString(map);
        response.getWriter().println(str);
    }
}
