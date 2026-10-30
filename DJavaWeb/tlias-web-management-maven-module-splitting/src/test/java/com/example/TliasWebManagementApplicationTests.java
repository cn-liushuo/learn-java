package com.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// @SpringBootTest
class TliasWebManagementApplicationTests {

    @Test
    public void testUuid() {
        for (int i = 0; i < 1000; i++) {
            String uuid = UUID.randomUUID().toString();
            System.out.println(uuid);
        }
    }

    /**
     * 生成 JWT
     */
    @Test
    public void testGenJwt() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("name", "Tom");
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "example") // 设置签名算法
                .setClaims(claims) // 自定义内容(载荷)
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // 设置有效期为1h
                .compact();
        System.out.println(jwt);
    }

    /**
     * 解析 JWT
     */
    @Test
    public void testParseJwt() {
        Claims claims = Jwts.parser()
                .setSigningKey("example")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiVG9tIiwiaWQiOjEsImV4cCI6MTc5MjE0Mzk4Nn0.5gp0l-txJD-j6LdSAxljbDigrS3ALe__zn0yAXCpQIc")
                .getBody();
        System.out.println(claims);
    }

}
