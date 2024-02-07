package com.msb.springsecurity_example.service.impl;

import com.msb.springsecurity_example.common.Constants;
import com.msb.springsecurity_example.common.ResponseResult;
import com.msb.springsecurity_example.entity.LoginUser;
import com.msb.springsecurity_example.entity.SysUser;
import com.msb.springsecurity_example.service.LoginService;
import com.msb.springsecurity_example.utils.JwtUtil;
import com.msb.springsecurity_example.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(SysUser sysUser) {
        // 1.调用AuthenticationManager的 authenticate方法,进行用户认证
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(sysUser.getUserName(), sysUser.getPassword());
        // 对authentication和UserDetails进行匹配
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 2.如果认证没有通过,给出错误提示
        if (Objects.isNull(authentication)) {
            throw new RuntimeException("登录失败");
        }

        // 3.如果认证通过,使用userId生成一个JWT,并将其保存到ResponseResult对象中返回
        // 3.1 获取经过身份验证的用户主体信息
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 3.2 获取userId，生成JWT
        String userId = loginUser.getSysUser().getUserId().toString();
        String jwt = JwtUtil.createJWT(userId);

        // 4.将用户信息存储在Redis中，在下一次请求时能够识别出用户,userid作为key
        redisCache.setCacheObject("login:" + userId, loginUser);

        // 5. 封装ResponseResult并返回
        HashMap<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return new ResponseResult(200, "登录成功！！", map);
    }

    @Override
    public ResponseResult logout() {
        // 获取当前用户的认证信息
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authenticationToken)) {
            throw new RuntimeException("获取用户认证信息失败，请重新登录!");
        }
        LoginUser loginUser = (LoginUser) authenticationToken.getPrincipal();
        Long userId = loginUser.getSysUser().getUserId();

        // 删除redis中的用户信息
        redisCache.deleteObject("login:" + userId);
        return new ResponseResult(200, "注销成功！");
    }

    /**
     * 带验证码登录
     *
     * @param userName
     * @param password
     * @param code
     * @param uuid
     * @return
     */
    @Override
    public String login(String username, String password, String code, String uuid) {
        //1. 从redis中获取验证码
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(captcha);

//        if (captcha == null || !code.equalsIgnoreCase(captcha)) {
//            throw new KaptchaNotMatchException("验证码错误!");
//        }

        // 对authentication河userDetails进行匹配
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        //2. 如果认证没有通过，给出错误提示
        if (Objects.isNull(authentication)) {
            throw new RuntimeException("登录失败！");
        }

        //3.如果认证通过,使用userId生成一个JWT,并将其保存到 ResponseResult对象中返回
        //3.1 获取经过身份验证的用户的主体信息
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        //3.2 获取到userID 生成JWT
        String userId = loginUser.getSysUser().getUserId().toString();
        String jwt = JwtUtil.createJWT(userId);

        //4.将用户信息存储在Redis中，在下一次请求时能够识别出用户,userid作为key
        redisCache.setCacheObject("login:" + userId, loginUser);

        //5.封装ResponseResult,并返回
        return jwt;
    }
}
