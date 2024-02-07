package com.msb.springsecurity_example.exception;

import com.alibaba.fastjson.JSON;
import com.msb.springsecurity_example.common.ResponseResult;
import com.msb.springsecurity_example.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义处理授权过程中的异常
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseResult result = new ResponseResult(HttpStatus.FORBIDDEN.value(),"权限不足,禁止访问");
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response,json);
    }
}
