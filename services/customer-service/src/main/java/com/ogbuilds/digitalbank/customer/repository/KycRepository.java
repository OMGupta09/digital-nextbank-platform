package com.ogbuilds.digitalbank.customer.repository;

import com.ogbuilds.digitalbank.customer.entity.Kyc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KycRepository
        extends JpaRepository<Kyc,Long> {

    Optional<Kyc> findByCustomerId(Long customerId);

}