package com.msb.springsecurity_example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class SpringsecurityExampleApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * 创建token
     */
    @Test
    public void testCreateJWT() {
        JwtBuilder builder = Jwts.builder()
                .setId("9527")                          // 唯一ID
                .setSubject("hejiayun_community")       // 主体内容
                .setIssuedAt(new Date())                // 签约时间
                .signWith(SignatureAlgorithm.HS256, "mashibing"); //
        // 设置签名，HS256，并设置secretkey

        // 压缩成string形式
        String jws = builder.compact();
        System.out.println(jws);
    }

    /**
     * 解析Token
     */
    @Test
    public void parserJWT() {
        String jws = "eyJhbGciOiJIUzI1NiJ9" +
                ".eyJqdGkiOiI5NTI3Iiwic3ViIjoiaGVqaWF5dW5fY29tbXVuaXR5IiwiaWF0IjoxNzA1Njg1NTMzfQ.aDdgZV_8ZM1Grsj3p0cn01KSl_ELt09LWWWo83GIqgQ";
        Claims claims = Jwts.parser().setSigningKey("mashibing")
                .parseClaimsJws(jws)
                .getBody();
        System.out.println(claims);
        // {jti=9527, sub=hejiayun_community, iat=1705685533}
    }

    /**
     * 设置过期时间
     */
    @Test
    public void testCreateJWT2() {
        long currentTimeMills = System.currentTimeMillis();
        // 过期时间就是现在
        Date expTime = new Date(currentTimeMills);

        JwtBuilder builder = Jwts.builder().
                setId("9527")                        // 唯一ID
                .setSubject("hejiayun_community")    // 主体内容
                .setIssuedAt(new Date())            // 签约时间
                .setExpiration(expTime)                // 设置过期时间
                .signWith(SignatureAlgorithm.HS256, "mashibing");
        // 设置签名，HS256，并设置secretkey

        // 压缩成string形式
        String jws = builder.compact();
        System.out.println(jws);
    }

    /**
     * 自定义claims
     */
    @Test
    public void testCreateJWT3() {
        long currentTimeMills = System.currentTimeMillis() + 10000000L;
        // 过期时间就是现在
        Date expTime = new Date(currentTimeMills);

        JwtBuilder builder = Jwts.builder().
                setId("9527")                        // 唯一ID
                .setSubject("hejiayun_community")    // 主体内容
                .setIssuedAt(new Date())             // 签约时间
                .setExpiration(expTime)              // 设置过期时间
                .claim("roles", "admin")
                .signWith(SignatureAlgorithm.HS256, "mashibing");
        // 设置签名，HS256，并设置secretkey

        // 压缩成string形式
        String jws = builder.compact();
        System.out.println(jws);
    }
}
