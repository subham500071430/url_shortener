package com.app.user.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.key}")
    private String secret_key;

    public String generateToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuer("url-shortener-app")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(new SecretKeySpec(secret_key.getBytes(), SignatureAlgorithm.HS256.getJcaName()))
                .compact();

    }
}
