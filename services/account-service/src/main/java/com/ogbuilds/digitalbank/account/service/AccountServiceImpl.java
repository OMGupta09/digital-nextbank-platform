package com.ogbuilds.digitalbank.account.service;

import com.ogbuilds.digitalbank.account.dto.request.CreateAccountRequest;
import com.ogbuilds.digitalbank.account.dto.response.AccountResponse;
import com.ogbuilds.digitalbank.account.entity.Account;
import com.ogbuilds.digitalbank.account.enums.AccountStatus;
import com.ogbuilds.digitalbank.account.exception.AccountNotFoundException;
import com.ogbuilds.digitalbank.account.repository.AccountRepository;
import com.ogbuilds.digitalbank.account.service.AccountService;
import com.ogbuilds.digitalbank.account.util.AccountNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl
        implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountNumberGenerator accountNumberGenerator;

    @Override
    public AccountResponse createAccount(
            CreateAccountRequest request) {

        String accountNumber;

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

        account = accountRepository.save(account);

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
    public AccountResponse getAccountById(Long id) {

        Account account =
                accountRepository.findById(id)

                        .orElseThrow(() ->
                                new AccountNotFoundException(
                                        "Account not found."
                                ));

        return map(account);

    }

    @Override
    public AccountResponse getAccountByNumber(
            String accountNumber) {

        Account account =
                accountRepository.findByAccountNumber(accountNumber)

                        .orElseThrow(() ->
                                new AccountNotFoundException(
                                        "Account not found."
                                ));

        return map(account);

    }

    @Override
    public List<AccountResponse> getAccountsByCustomer(
            Long customerId) {

        return accountRepository

                .findByCustomerId(customerId)

                .stream()

                .map(this::map)

                .toList();

    }

    @Override
    public AccountResponse updateAccountStatus(
            Long id,
            AccountStatus status) {

        Account account =
                accountRepository.findById(id)

                        .orElseThrow(() ->
                                new AccountNotFoundException(
                                        "Account not found."
                                ));

        account.setStatus(status);

        account.setUpdatedAt(LocalDateTime.now());

        account = accountRepository.save(account);

        return map(account);

    }

    @Override
    public void closeAccount(Long id) {

        Account account =
                accountRepository.findById(id)

                        .orElseThrow(() ->
                                new AccountNotFoundException(
                                        "Account not found."
                                ));

        account.setStatus(AccountStatus.CLOSED);

        account.setUpdatedAt(LocalDateTime.now());

        accountRepository.save(account);

    }

}