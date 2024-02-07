package com.msb.hjycommunity.system.service.impl;

import com.msb.hjycommunity.common.constant.Constants;
import com.msb.hjycommunity.common.constant.RedisCache;
import com.msb.hjycommunity.common.utils.UUIDUtils;
import com.msb.hjycommunity.system.domain.LoginUser;
import com.msb.hjycommunity.system.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements TokenService {
    // 毫秒
    private static final long MILLIS_SECOND = 1000;
    // 分钟
    private static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;
    // 20分钟
    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;
    @Autowired
    private RedisCache redisCache;
    // 令牌自定义标识
    @Value("${token.header:}")
    private String header;
    // 令牌秘钥
    @Value("${token.secret:}")
    private String secret;
    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime:}")
    private int expireTime;

    /**
     * 创建令牌
     *
     * @param loginUser
     * @return
     */
    @Override
    public String createToken(LoginUser loginUser) {
        //设置唯一用户标识
        String userKey = UUIDUtils.randomUUID();
        loginUser.setToken(userKey);

        // todo 保存用户信息 刷新令牌
        refreshToken(loginUser);

        HashMap<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, userKey);

        //创建token, 将用户唯一标识 通过setClaims方法 保存到token中
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    @Override
    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        // 过期时间30分钟
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisCache.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 拼接tokenkey
     *
     * @param uuid
     * @return
     */
    private String getTokenKey(String uuid) {
        return Constants.LOGIN_TOKEN_KEY + uuid;
    }

    /**
     * 从request的请求头中 获取token
     *
     * @param request
     * @return: java.lang.String
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(this.header);
        // JWT标准写法 Authorization: Bearer aaa.bb.cc
        if (!StringUtils.isEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    @Override
    public LoginUser getLoginUser(HttpServletRequest request) {
        String token = getToken(request);
        if (!StringUtils.isEmpty(token)) {
            Claims claims = parseToken(token);
            //解析对应的用户信息和权限信息
            String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
            String userKey = getTokenKey(uuid);
            LoginUser loginUser = redisCache.getCacheObject(userKey);
            return loginUser;
        }
        return null;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 验证令牌有效期,相差不足20分钟,自动刷新缓存
     *
     * @param loginUser
     */
    @Override
    public void verifyToken(LoginUser loginUser) {
        Long expireTime = loginUser.getExpireTime();
        long currentTimeMillis = System.currentTimeMillis();
        // 相差不足20分钟，自动刷新缓存
        if (expireTime - currentTimeMillis <= MILLIS_MINUTE_TEN) {
            refreshToken(loginUser);
        }
    }

    /**
     * 设置用户身份信息
     */
    @Override
    public void setLoginUser(LoginUser loginUser) {
        if (!Objects.isNull(loginUser) && !StringUtils.isEmpty(loginUser.getToken())) {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除用户身份信息
     */
    @Override
    public void delLoginUser(String token) {
        if (!StringUtils.isEmpty(token)) {
            String userKey = getTokenKey(token);
            redisCache.deleteObject(userKey);
        }
    }
}
