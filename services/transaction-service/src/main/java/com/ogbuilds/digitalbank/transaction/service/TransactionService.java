package com.ogbuilds.digitalbank.transaction.service;

import com.ogbuilds.digitalbank.transaction.dto.request.DepositRequest;
import com.ogbuilds.digitalbank.transaction.dto.request.TransferRequest;
import com.ogbuilds.digitalbank.transaction.dto.request.WithdrawRequest;
import com.ogbuilds.digitalbank.transaction.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {

    TransactionResponse deposit(
            DepositRequest request
    );

    TransactionResponse withdraw(
            WithdrawRequest request
    );

    TransactionResponse transfer(
            TransferRequest request
    );

    TransactionResponse getByReferenceId(
            String referenceId
    );

    List<TransactionResponse> getTransactionsByAccount(
            String accountNumber
    );

}