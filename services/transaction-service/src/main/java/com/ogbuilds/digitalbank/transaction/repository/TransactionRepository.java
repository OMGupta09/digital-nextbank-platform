package com.ogbuilds.digitalbank.transaction.repository;

import com.ogbuilds.digitalbank.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByReferenceId(
            String referenceId
    );

    List<Transaction> findByFromAccountNumber(
            String accountNumber
    );

    List<Transaction> findByToAccountNumber(
            String accountNumber
    );

}