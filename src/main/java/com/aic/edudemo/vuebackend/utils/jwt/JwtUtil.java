package com.aic.edudemo.vuebackend.utils.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.*;

public class JwtUtil {
    private static final String SECRET = "mySuperSecretKeyForJWT1234567890123456"; // 必須至少 32 bytes
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(Base64.getEncoder().encode(SECRET.getBytes()));
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 小時


    public static String generateToken(String username, Integer userId, List<String> roles) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("userName", username);
        claims.put("roles", roles);


        return Jwts.builder()
                .setSubject(username)
                .addClaims(claims) // ✅ 使用 `addClaims()`，而不是 `setClaims()`
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }



}

