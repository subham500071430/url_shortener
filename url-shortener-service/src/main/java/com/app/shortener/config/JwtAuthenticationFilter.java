package com.app.shortener.config;

import com.app.shortener.dto.PublicKeyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final RestTemplate restTemplate;

    @Autowired
    public JwtAuthenticationFilter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        boolean unauthorized = false;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                String kid = extractKidFromToken(token);
                PublicKeyResponse keyResponse = fetchPublicKeyByKid(kid);

                if (keyResponse == null) {
                    unauthorized = true;
                } else {
                    PublicKey publicKey = convertToPublicKey(keyResponse.getKey());

                    // invalid public key or token -- to-do
                    Claims claims = Jwts.parser()
                            .setSigningKey(publicKey)
                            .parseClaimsJws(token)
                            .getBody();

                    if (claims.getExpiration().before(new Date()) || claims.get("role") == null) {
                        unauthorized = true;
                    }
                }

            } catch (Exception e) {
                unauthorized = true;
            }
        }

        if (unauthorized) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String extractKidFromToken(String token) throws IOException {
        String[] parts = token.split("\\.");
        String headerJson = new String(Base64.getUrlDecoder().decode(parts[0]));
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> header = mapper.readValue(headerJson, Map.class);
        return (String) header.get("kid");
    }

    private PublicKeyResponse fetchPublicKeyByKid(String keyId) {
        String url = "http://auth-service/auth/public-key";

        ResponseEntity<List<PublicKeyResponse>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PublicKeyResponse>>() {}
        );

        List<PublicKeyResponse> keys = responseEntity.getBody();
        if (keys != null) {
            for (PublicKeyResponse key : keys) {
                if (key.getKid().equals(keyId)) {
                    return key;
                }
            }
        }
        return null;
    }

    private PublicKey convertToPublicKey(String base64Key) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }
}