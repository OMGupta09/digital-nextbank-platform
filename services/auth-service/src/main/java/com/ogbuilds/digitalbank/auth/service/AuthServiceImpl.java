package com.ogbuilds.digitalbank.auth.service;

import com.ogbuilds.digitalbank.auth.client.CustomerClient;
import com.ogbuilds.digitalbank.auth.dto.RegisterRequest;
import com.ogbuilds.digitalbank.auth.dto.RegisterResponse;
import com.ogbuilds.digitalbank.auth.dto.client.CreateCustomerRequest;
import com.ogbuilds.digitalbank.auth.entity.AuthUser;
import com.ogbuilds.digitalbank.auth.entity.Role;
import com.ogbuilds.digitalbank.auth.exception.UserAlreadyExistsException;
import com.ogbuilds.digitalbank.auth.repository.UserRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final CustomerClient customerClient;

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

        try {

            customerClient.createCustomer(
                    CreateCustomerRequest.builder()
                            .authUserId(user.getId())
                            .firstName(request.getFirstName())
                            .lastName(request.getLastName())
                            .phoneNumber(request.getPhoneNumber())
                            .dateOfBirth(request.getDateOfBirth())
                            .gender(request.getGender())
                            .build()
            );

        } catch (FeignException ex) {

            log.error("Customer profile creation failed for authUserId={}. Rolling back auth user.", user.getId(), ex);

            repository.delete(user);

            throw new RuntimeException(
                    "Registration failed: could not create customer profile. Please try again.", ex
            );

        }

        return RegisterResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .build();
    }

}