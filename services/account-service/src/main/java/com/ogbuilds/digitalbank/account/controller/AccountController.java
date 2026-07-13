package com.ogbuilds.digitalbank.account.controller;

import com.ogbuilds.digitalbank.account.dto.request.CreateAccountRequest;
import com.ogbuilds.digitalbank.account.dto.response.AccountResponse;
import com.ogbuilds.digitalbank.account.enums.AccountStatus;
import com.ogbuilds.digitalbank.account.service.AccountService;
import com.ogbuilds.digitalbank.account.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(
            @Valid @RequestBody CreateAccountRequest request) {

        AccountResponse response =
                accountService.createAccount(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<AccountResponse>builder()
                        .success(true)
                        .message("Account created successfully.")
                        .data(response)
                        .build());

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> getById(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long authUserId) {

        AccountResponse response =
                accountService.getAccountById(id, authUserId);

        return ResponseEntity.ok(
                ApiResponse.<AccountResponse>builder()
                        .success(true)
                        .message("Account fetched successfully.")
                        .data(response)
                        .build());

    }

    @GetMapping("/number/{accountNumber}")
    public ResponseEntity<ApiResponse<AccountResponse>> getByNumber(
            @PathVariable String accountNumber,
            @RequestHeader("X-User-Id") Long authUserId) {

        AccountResponse response =
                accountService.getAccountByNumber(accountNumber, authUserId);

        return ResponseEntity.ok(
                ApiResponse.<AccountResponse>builder()
                        .success(true)
                        .message("Account fetched successfully.")
                        .data(response)
                        .build());

    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getByCustomer(
            @PathVariable Long customerId,
            @RequestHeader("X-User-Id") Long authUserId) {

        List<AccountResponse> response =
                accountService.getAccountsByCustomer(customerId, authUserId);

        return ResponseEntity.ok(
                ApiResponse.<List<AccountResponse>>builder()
                        .success(true)
                        .message("Accounts fetched successfully.")
                        .data(response)
                        .build());

    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<AccountResponse>> updateStatus(
            @PathVariable Long id,
            @RequestParam AccountStatus status,
            @RequestHeader("X-User-Id") Long authUserId) {

        AccountResponse response =
                accountService.updateAccountStatus(id, status, authUserId);

        return ResponseEntity.ok(
                ApiResponse.<AccountResponse>builder()
                        .success(true)
                        .message("Account status updated.")
                        .data(response)
                        .build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> closeAccount(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long authUserId) {

        accountService.closeAccount(id, authUserId);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Account closed successfully.")
                        .build());

    }

    @PutMapping("/{accountNumber}/deposit")
    public ResponseEntity<ApiResponse<AccountResponse>> deposit(
            @PathVariable String accountNumber,
            @RequestParam BigDecimal amount,
            @RequestHeader("X-User-Id") Long authUserId
    ) {

        AccountResponse response =
                accountService.deposit(
                        accountNumber,
                        amount,
                        authUserId
                );

        return ResponseEntity.ok(
                ApiResponse.<AccountResponse>builder()
                        .success(true)
                        .message("Deposit successful.")
                        .data(response)
                        .build()
        );

    }

    @PutMapping("/{accountNumber}/withdraw")
    public ResponseEntity<ApiResponse<AccountResponse>> withdraw(
            @PathVariable String accountNumber,
            @RequestParam BigDecimal amount,
            @RequestHeader("X-User-Id") Long authUserId) {

        AccountResponse response =
                accountService.withdraw(
                        accountNumber,
                        amount,
                        authUserId
                );

        return ResponseEntity.ok(
                ApiResponse.<AccountResponse>builder()
                        .success(true)
                        .message("Withdrawal successful.")
                        .data(response)
                        .build()
        );

    }

}