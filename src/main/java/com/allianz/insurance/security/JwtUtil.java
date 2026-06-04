package com.allianz.insurance.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String email) {

        log.info("Generating JWT token for {}", email);

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + expiration))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {

        return extractClaims(token)
                .getSubject();
    }

    private Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public boolean isTokenValid(
            String token,
            String email) {

        return email.equals(extractUsername(token))
                && !isTokenExpired(token);
    }
    private boolean isTokenExpired(
            String token) {

        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }
}