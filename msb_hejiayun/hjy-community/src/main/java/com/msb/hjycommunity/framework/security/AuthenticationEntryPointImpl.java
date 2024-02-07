package com.msb.hjycommunity.framework.security;

import com.alibaba.fastjson.JSON;
import com.msb.hjycommunity.common.constant.HttpStatus;
import com.msb.hjycommunity.common.core.domain.BaseResponse;
import com.msb.hjycommunity.common.utils.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 401
        Integer code = HttpStatus.UNAUTHORIZED;
        ServletUtils.renderString(response, JSON.toJSONString(BaseResponse.fail(code.toString(), "认证失败，无法访问系统资源")));
    }
}
