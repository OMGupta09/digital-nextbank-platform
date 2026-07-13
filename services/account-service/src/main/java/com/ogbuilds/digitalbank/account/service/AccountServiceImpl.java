package com.ogbuilds.digitalbank.account.service;

import com.ogbuilds.digitalbank.account.client.CustomerClient;
import com.ogbuilds.digitalbank.account.dto.request.CreateAccountRequest;
import com.ogbuilds.digitalbank.account.dto.response.AccountResponse;
import com.ogbuilds.digitalbank.account.entity.Account;
import com.ogbuilds.digitalbank.account.enums.AccountStatus;
import com.ogbuilds.digitalbank.account.exception.AccessDeniedException;
import com.ogbuilds.digitalbank.account.exception.AccountNotFoundException;
import com.ogbuilds.digitalbank.account.exception.CustomerNotFoundException;
import com.ogbuilds.digitalbank.account.exception.InsufficientBalanceException;
import com.ogbuilds.digitalbank.account.repository.AccountRepository;
import com.ogbuilds.digitalbank.account.util.AccountNumberGenerator;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl
        implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountNumberGenerator accountNumberGenerator;
    private final CustomerClient customerClient;

    @Override
    public AccountResponse createAccount(
            CreateAccountRequest request) {

        log.info(
                "Creating account for customer {}",
                request.getCustomerId()
        );

        String accountNumber;
        try {

            customerClient.getCustomer(
                    request.getCustomerId()
            );

        } catch (FeignException.NotFound ex) {

            throw new CustomerNotFoundException(
                    "Customer not found."
            );
        }

        log.info(
                "Customer {} verified.",
                request.getCustomerId()
        );

        do {

            accountNumber =
                    accountNumberGenerator.generate();

        } while (accountRepository
                .existsByAccountNumber(accountNumber));

        LocalDateTime now = LocalDateTime.now();

        Account account = Account.builder()

                .accountNumber(accountNumber)

                .customerId(request.getCustomerId())

                .accountType(request.getAccountType())

                .status(AccountStatus.ACTIVE)

                .balance(request.getInitialDeposit())

                .currency("INR")

                .createdAt(now)

                .updatedAt(now)

                .build();

        log.info(
                "Generated account number {}",
                accountNumber
        );

        account = accountRepository.save(account);

        log.info(
                "Account {} created successfully.",
                account.getAccountNumber()
        );

        return map(account);
    }

    private AccountResponse map(Account account) {

        return AccountResponse.builder()

                .id(account.getId())

                .accountNumber(account.getAccountNumber())

                .customerId(account.getCustomerId())

                .accountType(account.getAccountType())

                .status(account.getStatus())

                .balance(account.getBalance())

                .currency(account.getCurrency())

                .build();

    }

    @Override
    public AccountResponse getAccountById(Long id,
                                          Long authUserId) {

        Account account =
                accountRepository.findById(id)

                        .orElseThrow(() ->
                                new AccountNotFoundException(
                                        "Account not found."
                                ));

        verifyOwnership(account, authUserId);

        return map(account);

    }

    @Override
    public AccountResponse getAccountByNumber(
            String accountNumber,
            Long authUserId) {

        Account account =
                accountRepository.findByAccountNumber(accountNumber)

                        .orElseThrow(() ->
                                new AccountNotFoundException(
                                        "Account not found."
                                ));

        verifyOwnership(account, authUserId);

        return map(account);

    }

    @Override
    public List<AccountResponse> getAccountsByCustomer(
            Long customerId,
            Long authUserId) {

        Long authenticatedCustomerId = customerClient
                .getCustomerByAuthUserId(authUserId)
                .getData()
                .getId();

        if (!authenticatedCustomerId.equals(customerId)) {
            throw new AccessDeniedException(
                    "You are not authorized to access these accounts."
            );
        }
        return accountRepository
                .findByCustomerId(customerId)
                .stream()
                .map(this::map)
                .toList();

    }

    @Override
    public AccountResponse updateAccountStatus(
            Long id,
            AccountStatus status,
            Long authUserId) {

        Account account =
                accountRepository.findById(id)

                        .orElseThrow(() ->
                                new AccountNotFoundException(
                                        "Account not found."
                                ));

        verifyOwnership(account, authUserId);

        account.setStatus(status);

        account.setUpdatedAt(LocalDateTime.now());

        account = accountRepository.save(account);

        return map(account);

    }

    @Override
    public void closeAccount(Long id,
                             Long authUserId) {

        Account account =
                accountRepository.findById(id)

                        .orElseThrow(() ->
                                new AccountNotFoundException(
                                        "Account not found."
                                ));

        verifyOwnership(account, authUserId);

        account.setStatus(AccountStatus.CLOSED);

        account.setUpdatedAt(LocalDateTime.now());

        accountRepository.save(account);

    }

    @Override
    public AccountResponse deposit(
            String accountNumber,
            BigDecimal amount,
            Long authUserId) {

        Account account =
                accountRepository.findByAccountNumber(accountNumber)

                        .orElseThrow(() ->
                                new AccountNotFoundException(
                                        "Account not found."
                                ));

        verifyOwnership(account, authUserId);

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Amount must be greater than zero."
            );

        }

        if (account.getStatus() != AccountStatus.ACTIVE) {

            throw new IllegalStateException(
                    "Account is not active."
            );

        }

        account.setBalance(
                account.getBalance().add(amount)
        );

        account.setUpdatedAt(LocalDateTime.now());

        account = accountRepository.save(account);

        log.info(
                "Deposited {} into account {}",
                amount,
                accountNumber
        );

        return map(account);

    }

    @Override
    public AccountResponse withdraw(
            String accountNumber,
            BigDecimal amount,
            Long authUserId) {

        Account account =
                accountRepository.findByAccountNumber(accountNumber)

                        .orElseThrow(() ->
                                new AccountNotFoundException(
                                        "Account not found."
                                ));

        verifyOwnership(account, authUserId);

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Amount must be greater than zero."
            );

        }

        if (account.getStatus() != AccountStatus.ACTIVE) {

            throw new IllegalStateException(
                    "Account is not active."
            );

        }

        if (account.getBalance().compareTo(amount) < 0) {

            throw new InsufficientBalanceException(
                    "Insufficient account balance."
            );

        }

        account.setBalance(
                account.getBalance().subtract(amount)
        );

        account.setUpdatedAt(LocalDateTime.now());

        account = accountRepository.save(account);

        log.info(
                "Withdrawn {} from account {}",
                amount,
                accountNumber
        );

        return map(account);

    }

    private void verifyOwnership(
            Account account,
            Long authUserId) {

        Long customerId = customerClient
                .getCustomerByAuthUserId(authUserId)
                .getData()
                .getId();

        if (!account.getCustomerId().equals(customerId)) {
            throw new AccessDeniedException(
                    "You do not own this account."
            );
        }

    }

}