package com.msb.springsecurity_example2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 自定义认证成功处理器
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "登录成功");
        map.put("status", 200);
        response.setContentType("application/json;charset=UTF-8");
        String str = new ObjectMapper().writeValueAsString(map);
        response.getWriter().println(str);
    }
}
