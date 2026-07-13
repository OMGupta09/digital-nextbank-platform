package com.ogbuilds.digitalbank.auth.security.jwt;

import com.ogbuilds.digitalbank.auth.entity.AuthUser;
import com.ogbuilds.digitalbank.auth.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiration}")
    private long accessExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshExpiration;

    private SecretKey getKey() {

        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );

    }

    @Override
    public String generateAccessToken(
            UserDetails userDetails) {

        return buildToken(
                userDetails,
                accessExpiration
        );

    }

    @Override
    public String generateRefreshToken(
            UserDetails userDetails) {

        return buildToken(
                userDetails,
                refreshExpiration
        );

    }

    private String buildToken(
            UserDetails userDetails,
            long expiration) {

        CustomUserDetails customUserDetails =
                (CustomUserDetails) userDetails;

        AuthUser user =
                customUserDetails.getUser();

        return Jwts.builder()

                .subject(user.getEmail())

                .claim("userId", user.getId())

                .claim("role", user.getRole().name())

                .issuedAt(new Date())

                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + expiration
                        )
                )

                .signWith(getKey())

                .compact();

    }

    @Override
    public String extractUsername(
            String token) {

        return extractClaim(
                token,
                Claims::getSubject
        );

    }

    public <T> T extractClaim(
            String token,
            Function<Claims, T> resolver) {

        Claims claims = Jwts.parser()

                .verifyWith(getKey())

                .build()

                .parseSignedClaims(token)

                .getPayload();

        return resolver.apply(claims);

    }

    @Override
    public boolean isTokenValid(
            String token,
            UserDetails userDetails) {

        String username =
                extractUsername(token);

        return username.equals(
                userDetails.getUsername()
        ) && !isExpired(token);

    }

    private boolean isExpired(
            String token) {

        return extractClaim(
                token,
                Claims::getExpiration
        ).before(new Date());

    }

}