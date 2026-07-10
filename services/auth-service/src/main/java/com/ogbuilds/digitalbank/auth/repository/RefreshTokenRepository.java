package com.ogbuilds.digitalbank.auth.repository;

import com.ogbuilds.digitalbank.auth.entity.RefreshToken;
import com.ogbuilds.digitalbank.auth.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(AuthUser user);

    void deleteByUser(AuthUser user);

}