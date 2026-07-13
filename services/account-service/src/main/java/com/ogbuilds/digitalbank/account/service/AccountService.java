package com.ogbuilds.digitalbank.account.service;

import com.ogbuilds.digitalbank.account.dto.request.CreateAccountRequest;
import com.ogbuilds.digitalbank.account.dto.response.AccountResponse;
import com.ogbuilds.digitalbank.account.enums.AccountStatus;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    AccountResponse createAccount(
            CreateAccountRequest request
    );

    AccountResponse getAccountById(
            Long id,
            Long authUserId
    );

    AccountResponse getAccountByNumber(
            String accountNumber,
            Long authUserId
    );

    List<AccountResponse> getAccountsByCustomer(
            Long customerId,
            Long authUserId
    );

    AccountResponse updateAccountStatus(
            Long id,
            AccountStatus status,
            Long authUserId
    );

    void closeAccount(
            Long id,
            Long authUserId
    );

    AccountResponse deposit(
            String accountNumber,
            BigDecimal amount,
            Long authUserId
    );

    AccountResponse withdraw(
            String accountNumber,
            BigDecimal amount,
            Long authUserId
    );

}