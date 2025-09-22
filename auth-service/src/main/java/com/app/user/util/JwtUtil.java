package com.app.user.util;

import com.app.user.dto.PublicKeyDTO;
import com.app.user.entity.PublicKeyStore;
import com.app.user.repository.PublicKeyRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PublicKeyRepository repository;

    public String generateToken(String username, String role, int exp) {

        try {

            Map<String, Object> claims = new HashMap<>();
            claims.put("role", role);
            claims.put("kid", "key1");

            KeyStore keyStore = KeyStore.getInstance("jks");

            keyStore.load(new FileInputStream("C:/Users/subha/IdeaProjects/url_shortener" +
                            "/auth-service/src/main/resources/mykeystore.jks"),
                    "changeit".toCharArray());

            PrivateKey privateKey = (PrivateKey) keyStore.getKey("key1", "changeit".toCharArray());
            Certificate certificate = keyStore.getCertificate("key1");
            PublicKey publicKey = certificate.getPublicKey();
            PublicKeyDTO publicKeyDTO = new PublicKeyDTO("key1", Base64.getEncoder().encodeToString(publicKey.getEncoded()));
            PublicKeyStore store = modelMapper.map(publicKeyDTO, PublicKeyStore.class);
            repository.save(store);

            return Jwts.builder()
                    .setSubject(username)
                    .setIssuer("url-shortener-app")
                    .setClaims(claims)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + exp))
                    .signWith(privateKey)
                    .compact();

        } catch (Exception e) {

            return null;
        }

    }

    public boolean isValidToken(String token) {

        try {
            KeyStore keyStore = KeyStore.getInstance("jks");

            keyStore.load(new FileInputStream("C:/Users/subha/IdeaProjects/url_shortener" +
                            "/auth-service/src/main/resources/mykeystore.jks"),
                    "changeit".toCharArray());

            PrivateKey privateKey = (PrivateKey) keyStore.getKey("key1", "changeit".toCharArray());

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(privateKey).build().
                    parseClaimsJws(token)
                    .getBody();

            return claims.getExpiration().before(new Date());

        } catch (Exception e) {

            return false;
        }
    }
}
