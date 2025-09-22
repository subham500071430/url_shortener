package com.app.shortener.config;

import com.app.shortener.dto.PublicKeyResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final PublicKey publicKey;

    RestTemplate restTemplate;

    public JwtAuthenticationFilter() throws Exception {

        String url = "https://localhost:8081/auth/public-key";
        PublicKeyResponse response = restTemplate.getForObject(url, PublicKeyResponse.class);

        String base64Key = response.getKey();
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        publicKey = keyFactory.generatePublic(keySpec);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(publicKey)
                        .parseClaimsJws(token).getBody();

                String username = claims.getSubject();

                if (claims.getExpiration().after(new Date()) || claims.get("role") == null) {
                    response.setStatus(401);
                    return;
                }

            } catch (Exception e) {
                response.setStatus(401);
                return;
            }

        }

        filterChain.doFilter(request, response);
    }
}
