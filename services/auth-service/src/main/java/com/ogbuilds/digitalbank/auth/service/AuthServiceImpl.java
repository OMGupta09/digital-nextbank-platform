package com.ogbuilds.digitalbank.auth.service;

import com.ogbuilds.digitalbank.auth.dto.RegisterRequest;
import com.ogbuilds.digitalbank.auth.dto.RegisterResponse;
import com.ogbuilds.digitalbank.auth.entity.AuthUser;
import com.ogbuilds.digitalbank.auth.entity.Role;
import com.ogbuilds.digitalbank.auth.exception.UserAlreadyExistsException;
import com.ogbuilds.digitalbank.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (repository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered.");
        }

        AuthUser user = AuthUser.builder()
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .enabled(true)
                .build();

        user = repository.save(user);

        return RegisterResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .build();
    }

}