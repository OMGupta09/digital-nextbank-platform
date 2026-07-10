package com.ogbuilds.digitalbank.account.service;

import com.ogbuilds.digitalbank.account.dto.request.CreateAccountRequest;
import com.ogbuilds.digitalbank.account.dto.response.AccountResponse;
import com.ogbuilds.digitalbank.account.enums.AccountStatus;

import java.util.List;

public interface AccountService {

    AccountResponse createAccount(
            CreateAccountRequest request
    );

    AccountResponse getAccountById(
            Long id
    );

    AccountResponse getAccountByNumber(
            String accountNumber
    );

    List<AccountResponse> getAccountsByCustomer(
            Long customerId
    );

    AccountResponse updateAccountStatus(
            Long id,
            AccountStatus status
    );

    void closeAccount(
            Long id
    );

}