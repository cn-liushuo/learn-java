package com.example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Map;

public class JwtUtils {

    private static String signKey = "example";
    // 不设置过期时间：token 永久有效（仅建议本地学习环境使用，生产环境请保留有效期）

    /**
     * 生成 JWT 令牌
     *
     * @param claims JWT 第二部门负载 payload 中存储的内容
     * @return
     */
    public static String generateJwt(Map<String, Object> claims) {
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, signKey) // 设置签名算法
                .setClaims(claims) // 自定义内容(载荷)
                // 不调用 setExpiration，JWT 就没有 exp 声明，解析时不会过期
                .compact();
        return jwt;
    }

    /**
     * 解析 JWT 令牌
     *
     * @param jwt JWT 令牌
     * @return JWT 第二部分负载 payload 中存储的内容
     */
    public static Claims parseJwt(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }
}
