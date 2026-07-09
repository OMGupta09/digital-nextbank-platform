package com.ogbuilds.digitalbank.auth.controller;

import com.ogbuilds.digitalbank.auth.dto.*;
import com.ogbuilds.digitalbank.auth.service.AuthService;
import com.ogbuilds.digitalbank.auth.service.AuthenticationService;
import com.ogbuilds.digitalbank.auth.service.RefreshTokenService;
import com.ogbuilds.digitalbank.auth.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        RegisterResponse response = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<RegisterResponse>builder()
                        .success(true)
                        .message("User registered successfully.")
                        .data(response)
                        .build());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response =
                authenticationService.login(request);

        return ResponseEntity.ok(

                ApiResponse.<LoginResponse>builder()

                        .success(true)

                        .message("Login successful")

                        .data(response)

                        .build()

        );

    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponse>> refresh(
            @RequestBody RefreshTokenRequest request){

        LoginResponse response =
                refreshTokenService.refresh(request);

        return ResponseEntity.ok(

                ApiResponse.<LoginResponse>builder()
                        .success(true)
                        .message("Token Refreshed")
                        .data(response)
                        .build()

        );

    }

    @GetMapping("/me")
    public ResponseEntity<String> me() {
        return ResponseEntity.ok("Authenticated Successfully");
    }

}