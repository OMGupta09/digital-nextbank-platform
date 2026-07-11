package com.ogbuilds.digitalbank.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    private Claims claims(String token) {

        return Jwts.parser()

                .verifyWith(key)

                .build()

                .parseSignedClaims(token)

                .getPayload();

    }

    @Override
    public boolean isTokenValid(String token) {

        try {

            claims(token);

            return true;

        } catch (Exception e) {

            return false;

        }

    }

    @Override
    public String extractUsername(String token) {

        return claims(token).getSubject();

    }

    @Override
    public Long extractUserId(String token) {

        Object value =
                claims(token).get("userId");

        if (value instanceof Integer i)
            return i.longValue();

        if (value instanceof Long l)
            return l;

        return Long.parseLong(value.toString());

    }

    @Override
    public String extractRole(String token) {

        return claims(token)
                .get("role", String.class);

    }

}