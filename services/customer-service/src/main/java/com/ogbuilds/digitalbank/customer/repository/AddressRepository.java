package com.ogbuilds.digitalbank.customer.repository;

import com.ogbuilds.digitalbank.customer.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository
        extends JpaRepository<Address,Long> {

    Optional<Address> findByCustomerId(Long customerId);

}
