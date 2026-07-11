package com.ogbuilds.digitalbank.transaction.controller;

import com.ogbuilds.digitalbank.transaction.dto.request.DepositRequest;
import com.ogbuilds.digitalbank.transaction.dto.request.TransferRequest;
import com.ogbuilds.digitalbank.transaction.dto.request.WithdrawRequest;
import com.ogbuilds.digitalbank.transaction.dto.response.TransactionResponse;
import com.ogbuilds.digitalbank.transaction.service.TransactionService;
import com.ogbuilds.digitalbank.transaction.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<ApiResponse<TransactionResponse>> deposit(
            @Valid @RequestBody DepositRequest request) {

        TransactionResponse response =
                transactionService.deposit(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<TransactionResponse>builder()
                        .success(true)
                        .message("Deposit successful.")
                        .data(response)
                        .build());

    }

    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<TransactionResponse>> withdraw(
            @Valid @RequestBody WithdrawRequest request) {

        TransactionResponse response =
                transactionService.withdraw(request);

        return ResponseEntity.ok(
                ApiResponse.<TransactionResponse>builder()
                        .success(true)
                        .message("Withdrawal successful.")
                        .data(response)
                        .build());

    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<TransactionResponse>> transfer(
            @Valid @RequestBody TransferRequest request) {

        TransactionResponse response =
                transactionService.transfer(request);

        return ResponseEntity.ok(
                ApiResponse.<TransactionResponse>builder()
                        .success(true)
                        .message("Transfer successful.")
                        .data(response)
                        .build());

    }

    @GetMapping("/{referenceId}")
    public ResponseEntity<ApiResponse<TransactionResponse>> getByReferenceId(
            @PathVariable String referenceId) {

        TransactionResponse response =
                transactionService.getByReferenceId(referenceId);

        return ResponseEntity.ok(
                ApiResponse.<TransactionResponse>builder()
                        .success(true)
                        .message("Transaction fetched successfully.")
                        .data(response)
                        .build()
        );

    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getTransactions(
            @PathVariable String accountNumber) {

        List<TransactionResponse> response =
                transactionService.getTransactionsByAccount(accountNumber);

        return ResponseEntity.ok(
                ApiResponse.<List<TransactionResponse>>builder()
                        .success(true)
                        .message("Transactions fetched successfully.")
                        .data(response)
                        .build()
        );

    }

}