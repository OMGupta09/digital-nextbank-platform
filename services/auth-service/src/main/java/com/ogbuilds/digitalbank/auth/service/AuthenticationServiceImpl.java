package com.ogbuilds.digitalbank.auth.service;

import com.ogbuilds.digitalbank.auth.dto.LoginRequest;
import com.ogbuilds.digitalbank.auth.dto.LoginResponse;
import com.ogbuilds.digitalbank.auth.entity.RefreshToken;
import com.ogbuilds.digitalbank.auth.repository.RefreshTokenRepository;
import com.ogbuilds.digitalbank.auth.security.CustomUserDetails;
import com.ogbuilds.digitalbank.auth.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl
        implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest request) {

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetails user = (UserDetails) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshTokenValue = jwtService.generateRefreshToken(user);

        RefreshToken refreshToken = RefreshToken.builder()
                .token(refreshTokenValue)
                .user(((CustomUserDetails) user).getUser())
                .expiryDate(LocalDateTime.now().plusDays(7))
                .build();

        refreshTokenRepository.save(refreshToken);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenValue)
                .build();

    }

}