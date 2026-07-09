package com.ogbuilds.digitalbank.auth.service;

import com.ogbuilds.digitalbank.auth.dto.LoginResponse;
import com.ogbuilds.digitalbank.auth.dto.RefreshTokenRequest;
import com.ogbuilds.digitalbank.auth.entity.RefreshToken;
import com.ogbuilds.digitalbank.auth.repository.RefreshTokenRepository;
import com.ogbuilds.digitalbank.auth.security.CustomUserDetails;
import com.ogbuilds.digitalbank.auth.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl
        implements RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    public LoginResponse refresh(RefreshTokenRequest request) {

        RefreshToken refreshToken =
                repository.findByToken(request.getRefreshToken())
                        .orElseThrow(() ->
                                new RuntimeException("Refresh Token Invalid"));

        CustomUserDetails user =
                (CustomUserDetails)
                        userDetailsService.loadUserByUsername(
                                refreshToken.getUser().getEmail());

        String access =
                jwtService.generateAccessToken(user);

        String refresh =
                jwtService.generateRefreshToken(user);

        refreshToken.setToken(refresh);

        repository.save(refreshToken);

        return LoginResponse.builder()
                .accessToken(access)
                .refreshToken(refresh)
                .build();

    }

}