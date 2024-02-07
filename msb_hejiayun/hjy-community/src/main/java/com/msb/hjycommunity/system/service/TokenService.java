package com.msb.hjycommunity.system.service;

import com.msb.hjycommunity.system.domain.LoginUser;

import javax.servlet.http.HttpServletRequest;

/**
 * token验证处理
 */
public interface TokenService {
    /**
     * 创建令牌
     *
     * @param loginUser
     * @return
     */
    public String createToken(LoginUser loginUser);


    /**
     * 缓存用户信息 & 刷新令牌有效期
     *
     * @param loginUser
     */
    public void refreshToken(LoginUser loginUser);

    /**
     * 从Redis获取用户身份信息
     */
    LoginUser getLoginUser(HttpServletRequest request);

    /**
     * 验证令牌有效期,相差不足20分钟,自动刷新缓存
     */
    public void verifyToken(LoginUser loginUser);

    /**
     * 设置用户身份信息
     *
     * @param loginUser
     */
    public void setLoginUser(LoginUser loginUser);

    /**
     * 删除用户身份信息
     *
     * @param token
     */
    public void delLoginUser(String token);
}
