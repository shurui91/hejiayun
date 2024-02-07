package com.msb.hjycommunity.system.service.impl;

import com.msb.hjycommunity.common.constant.Constants;
import com.msb.hjycommunity.common.constant.RedisCache;
import com.msb.hjycommunity.common.core.exception.BaseException;
import com.msb.hjycommunity.common.core.exception.UserPasswordNotMatchException;
import com.msb.hjycommunity.framework.security.KaptchaNotMatchException;
import com.msb.hjycommunity.system.domain.LoginUser;
import com.msb.hjycommunity.system.service.SysLoginService;
import com.msb.hjycommunity.system.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SysLoginServiceImpl implements SysLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private TokenService tokenService;

    /**
     * 带验证码登录
     *
     * @param username
     * @param password
     * @param code
     * @param uuid
     * @return
     */
    @Override
    public String login(String username, String password, String code, String uuid) {
        //1.从redis中获取验证码,判断是否正确
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null || !code.equalsIgnoreCase(captcha)) {
            throw new KaptchaNotMatchException("验证码错误!");
        }

        //2.进行用户认证
        Authentication authentication = null;
        try {
            //该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new UserPasswordNotMatchException();
        }

        //3. 获取经过身份验证的用户的主体信息
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        //4.调用TokenService 生成token
        return tokenService.createToken(loginUser);
    }
}
