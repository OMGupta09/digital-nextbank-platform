package com.ogbuilds.digitalbank.gateway.security;

public interface JwtService {

    boolean isTokenValid(String token);

    String extractUsername(String token);

    Long extractUserId(String token);

    String extractRole(String token);

}