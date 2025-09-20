package com.app.user.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    public String generateToken(String username, String role , int exp) {

        try {

            Map<String, Object> claims = new HashMap<>();
            claims.put("role", role);
            claims.put("kid", "key1");

            KeyStore keyStore = KeyStore.getInstance("jks");

            keyStore.load(new FileInputStream("C:/Users/subha/IdeaProjects/url_shortener" +
                            "/user-service/src/main/resources/mykeystore.jks"),
                    "changeit".toCharArray());

            PrivateKey privateKey = (PrivateKey) keyStore.getKey("key1", "changeit".toCharArray());
            Certificate certificate = keyStore.getCertificate("key1");
            PublicKey publicKey = certificate.getPublicKey();

            return Jwts.builder()
                    .setSubject(username)
                    .setIssuer("url-shortener-app")
                    .setClaims(claims)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + exp))
                    .signWith(privateKey)
                    .compact();

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }

    }

    public boolean isValidToken(String token) {

        try {
            KeyStore keyStore = KeyStore.getInstance("jks");

            keyStore.load(new FileInputStream("C:/Users/subha/IdeaProjects/url_shortener" +
                            "/user-service/src/main/resources/mykeystore.jks"),
                    "changeit".toCharArray());

            PrivateKey privateKey = (PrivateKey) keyStore.getKey("key1", "changeit".toCharArray());

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(privateKey).build().
                    parseClaimsJws(token)
                    .getBody();

            return claims.getExpiration().before(new Date());

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
