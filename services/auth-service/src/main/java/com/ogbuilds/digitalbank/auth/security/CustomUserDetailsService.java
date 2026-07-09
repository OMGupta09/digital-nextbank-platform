package com.ogbuilds.digitalbank.auth.security;

import com.ogbuilds.digitalbank.auth.entity.AuthUser;
import com.ogbuilds.digitalbank.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        AuthUser user = repository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return new CustomUserDetails(user);
    }

}