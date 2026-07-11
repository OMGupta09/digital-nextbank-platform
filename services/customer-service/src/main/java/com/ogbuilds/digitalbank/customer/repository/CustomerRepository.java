package com.ogbuilds.digitalbank.customer.repository;

import com.ogbuilds.digitalbank.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository
        extends JpaRepository<Customer,Long> {

    Optional<Customer> findByAuthUserId(Long authUserId);

    boolean existsByPhoneNumber(String phoneNumber);

}