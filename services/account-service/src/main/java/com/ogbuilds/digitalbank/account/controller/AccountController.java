package com.ogbuilds.digitalbank.account.controller;

import com.ogbuilds.digitalbank.account.dto.request.CreateAccountRequest;
import com.ogbuilds.digitalbank.account.dto.response.AccountResponse;
import com.ogbuilds.digitalbank.account.enums.AccountStatus;
import com.ogbuilds.digitalbank.account.security.AccessGuard;
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
    private final AccessGuard accessGuard;

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
            @RequestHeader("X-User-Id") Long requesterAuthUserId,
            @RequestHeader("X-User-Role") String role) {

        AccountResponse response =
                accountService.getAccountById(id);

        accessGuard.requireOwnedAccountOrAdmin(response.getCustomerId(), requesterAuthUserId, role);

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
            @RequestHeader("X-User-Id") Long requesterAuthUserId,
            @RequestHeader("X-User-Role") String role) {

        AccountResponse response =
                accountService.getAccountByNumber(accountNumber);

        accessGuard.requireOwnedAccountOrAdmin(response.getCustomerId(), requesterAuthUserId, role);

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
            @RequestHeader("X-User-Id") Long requesterAuthUserId,
            @RequestHeader("X-User-Role") String role) {

        accessGuard.requireOwnedAccountOrAdmin(customerId, requesterAuthUserId, role);

        List<AccountResponse> response =
                accountService.getAccountsByCustomer(customerId);

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
            @RequestHeader("X-User-Role") String role) {

        accessGuard.requireAdmin(role);

        AccountResponse response =
                accountService.updateAccountStatus(id, status);

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
            @RequestHeader("X-User-Id") Long requesterAuthUserId,
            @RequestHeader("X-User-Role") String role) {

        AccountResponse existing = accountService.getAccountById(id);

        accessGuard.requireOwnedAccountOrAdmin(existing.getCustomerId(), requesterAuthUserId, role);

        accountService.closeAccount(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Account closed successfully.")
                        .build());

    }

    @PutMapping("/{accountNumber}/deposit")
    public ResponseEntity<ApiResponse<AccountResponse>> deposit(
            @PathVariable String accountNumber,
            @RequestParam BigDecimal amount
    ) {

        AccountResponse response =
                accountService.deposit(
                        accountNumber,
                        amount
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
            @RequestParam BigDecimal amount) {

        AccountResponse response =
                accountService.withdraw(
                        accountNumber,
                        amount
                );

        return ResponseEntity.ok(
                ApiResponse.<AccountResponse>builder()
                        .success(true)
                        .message("Withdrawal successful.")
                        .data(response)
                        .build()
        );

    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getMyAccounts(
            @RequestHeader("X-User-Id") Long authUserId) {

        List<AccountResponse> response =
                accountService.getMyAccounts(authUserId);

        return ResponseEntity.ok(
                ApiResponse.<List<AccountResponse>>builder()
                        .success(true)
                        .message("Accounts fetched successfully.")
                        .data(response)
                        .build());

    }

}