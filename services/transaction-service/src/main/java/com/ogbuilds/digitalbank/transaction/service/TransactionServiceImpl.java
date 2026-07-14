package com.ogbuilds.digitalbank.transaction.service;

import com.ogbuilds.digitalbank.transaction.client.AccountClient;
import com.ogbuilds.digitalbank.transaction.client.CustomerClient;
import com.ogbuilds.digitalbank.transaction.dto.client.AccountResponse;
import com.ogbuilds.digitalbank.transaction.dto.event.TransactionCompletedEvent;
import com.ogbuilds.digitalbank.transaction.dto.event.TransactionFailedEvent;
import com.ogbuilds.digitalbank.transaction.dto.request.DepositRequest;
import com.ogbuilds.digitalbank.transaction.dto.request.TransferRequest;
import com.ogbuilds.digitalbank.transaction.dto.request.WithdrawRequest;
import com.ogbuilds.digitalbank.transaction.dto.response.TransactionResponse;
import com.ogbuilds.digitalbank.transaction.entity.Transaction;
import com.ogbuilds.digitalbank.transaction.entity.TransactionStatus;
import com.ogbuilds.digitalbank.transaction.entity.TransactionType;
import com.ogbuilds.digitalbank.transaction.exception.TransactionNotFoundException;
import com.ogbuilds.digitalbank.transaction.exception.TransferFailedException;
import com.ogbuilds.digitalbank.transaction.producer.TransactionEventProducer;
import com.ogbuilds.digitalbank.transaction.repository.TransactionRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountClient accountClient;
    private final TransactionEventProducer transactionEventProducer;
    private final CustomerClient customerClient;

    private TransactionResponse map(Transaction transaction) {

        return TransactionResponse.builder().id(transaction.getId()).referenceId(transaction.getReferenceId()).fromAccountNumber(transaction.getFromAccountNumber()).toAccountNumber(transaction.getToAccountNumber()).amount(transaction.getAmount()).transactionType(transaction.getTransactionType()).status(transaction.getStatus()).description(transaction.getDescription()).createdAt(transaction.getCreatedAt()).build();

    }

    @Override
    @CacheEvict(cacheNames = "transactionHistory", key = "#request.accountNumber")
    @Transactional
    public TransactionResponse deposit(DepositRequest request) {

        log.info("Deposit requested for account {}", request.getAccountNumber());

        accountClient.deposit(request.getAccountNumber(), request.getAmount());

        Transaction transaction = Transaction.builder()

                .referenceId(UUID.randomUUID().toString())

                .fromAccountNumber(request.getAccountNumber())

                .amount(request.getAmount())

                .transactionType(TransactionType.DEPOSIT)

                .status(TransactionStatus.SUCCESS)

                .description(request.getDescription())

                .createdAt(LocalDateTime.now())

                .build();

        transaction = transactionRepository.save(transaction);

        log.info("Deposit completed.");

        return map(transaction);

    }

    @Override
    @CacheEvict(cacheNames = "transactionHistory", key = "#request.accountNumber")
    @Transactional
    public TransactionResponse withdraw(WithdrawRequest request) {

        log.info("Withdrawal requested for account {}", request.getAccountNumber());

        accountClient.withdraw(request.getAccountNumber(), request.getAmount());

        Transaction transaction = Transaction.builder()

                .referenceId(UUID.randomUUID().toString())

                .fromAccountNumber(request.getAccountNumber())

                .amount(request.getAmount())

                .transactionType(TransactionType.WITHDRAW)

                .status(TransactionStatus.SUCCESS)

                .description(request.getDescription())

                .createdAt(LocalDateTime.now())

                .build();

        transaction = transactionRepository.save(transaction);

        log.info("Withdrawal completed.");

        return map(transaction);

    }

    @Override
    @Caching(evict = {

            @CacheEvict(cacheNames = "transactionHistory", key = "#request.fromAccountNumber"),

            @CacheEvict(cacheNames = "transactionHistory", key = "#request.toAccountNumber")

    })
    @Transactional
    public TransactionResponse transfer(TransferRequest request) {

        log.info("Transfer started: {} -> {} Amount={}", request.getFromAccountNumber(), request.getToAccountNumber(), request.getAmount());

        Transaction transaction = createTransaction(request, TransactionStatus.PENDING);

        boolean withdrawn = false;

        try {

            accountClient.withdraw(request.getFromAccountNumber(), request.getAmount());

            withdrawn = true;

            log.info("Withdrawal completed for account {}", request.getFromAccountNumber());

            accountClient.deposit(request.getToAccountNumber(), request.getAmount());

            log.info("Deposit completed for account {}", request.getToAccountNumber());

            transaction.setStatus(TransactionStatus.SUCCESS);

            transaction = transactionRepository.save(transaction);

            transactionEventProducer.publishCompleted(buildCompletedEvent(transaction));

            log.info("Transfer completed successfully. Reference ID: {}", transaction.getReferenceId());

            return map(transaction);

        } catch (FeignException ex) {

            log.error("Transfer failed for {} -> {}. Starting compensation.", request.getFromAccountNumber(), request.getToAccountNumber(), ex);

            if (withdrawn) {

                try {

                    compensateTransfer(request);

                    log.info("Compensation completed successfully.");

                } catch (FeignException compensationEx) {

                    log.error("Compensation failed. Manual intervention required.", compensationEx);

                }

            }

            transaction.setStatus(TransactionStatus.FAILED);

            transaction = transactionRepository.save(transaction);

            transactionEventProducer.publishFailed(buildFailedEvent(transaction, ex.getMessage()));

            throw new TransferFailedException("Transfer failed. Funds have been returned to the source account");

        }

    }

    @Override
    @Cacheable(cacheNames = "transactions", key = "#referenceId")
    public TransactionResponse getByReferenceId(String referenceId) {

        Transaction transaction = transactionRepository.findByReferenceId(referenceId).orElseThrow(() -> new TransactionNotFoundException("Transaction not found."));

        return map(transaction);

    }

    @Override
    public List<TransactionResponse> getTransactionsByAccount(String accountNumber) {

        return transactionRepository

                .findByFromAccountNumber(accountNumber)

                .stream()

                .map(this::map)

                .toList();

    }

    private Transaction createTransaction(TransferRequest request, TransactionStatus status) {

        return Transaction.builder()

                .referenceId(UUID.randomUUID().toString())

                .fromAccountNumber(request.getFromAccountNumber())

                .toAccountNumber(request.getToAccountNumber())

                .amount(request.getAmount())

                .transactionType(TransactionType.TRANSFER)

                .status(status)

                .description(request.getDescription())

                .createdAt(LocalDateTime.now())

                .build();

    }

    private void compensateTransfer(TransferRequest request) {

        accountClient.deposit(request.getFromAccountNumber(), request.getAmount());

    }

    private TransactionCompletedEvent buildCompletedEvent(Transaction transaction) {

        return TransactionCompletedEvent.builder().referenceId(transaction.getReferenceId()).fromAccountNumber(transaction.getFromAccountNumber()).toAccountNumber(transaction.getToAccountNumber()).amount(transaction.getAmount()).transactionType(transaction.getTransactionType().name()).createdAt(transaction.getCreatedAt()).build();

    }

    private TransactionFailedEvent buildFailedEvent(Transaction transaction, String reason) {

        return TransactionFailedEvent.builder().referenceId(transaction.getReferenceId()).fromAccountNumber(transaction.getFromAccountNumber()).toAccountNumber(transaction.getToAccountNumber()).amount(transaction.getAmount()).transactionType(transaction.getTransactionType().name()).reason(reason).createdAt(transaction.getCreatedAt()).build();

    }

    @Override
    public List<TransactionResponse> getMyTransactions(Long authUserId) {

        Long customerId = customerClient.getCustomerByAuthUserId(authUserId).getData().getId();

        List<AccountResponse> accounts = accountClient.getAccountsByCustomer(customerId).getData();

        return accounts.stream()

                .flatMap(account -> {

                    List<Transaction> sent = transactionRepository.findByFromAccountNumber(account.getAccountNumber());

                    List<Transaction> received = transactionRepository.findByToAccountNumber(account.getAccountNumber());

                    return java.util.stream.Stream.concat(sent.stream(), received.stream());

                })

                .collect(java.util.stream.Collectors.toMap(Transaction::getId, t -> t, (a, b) -> a, java.util.LinkedHashMap::new))

                .values()

                .stream()

                .sorted(java.util.Comparator.comparing(Transaction::getCreatedAt).reversed())

                .map(this::map)

                .toList();
    }

}