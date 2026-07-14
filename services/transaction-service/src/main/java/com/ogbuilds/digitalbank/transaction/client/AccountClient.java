package com.ogbuilds.digitalbank.transaction.client;

import com.ogbuilds.digitalbank.transaction.dto.client.AccountResponse;
import com.ogbuilds.digitalbank.transaction.util.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(name = "ACCOUNT-SERVICE")
public interface AccountClient {

    @GetMapping("/accounts/number/{accountNumber}")
    ApiResponse<AccountResponse> getAccount(
            @PathVariable String accountNumber
    );

    @PutMapping("/accounts/{accountNumber}/deposit")
    ApiResponse<AccountResponse> deposit(
            @PathVariable String accountNumber,
            @RequestParam BigDecimal amount
    );

    @PutMapping("/accounts/{accountNumber}/withdraw")
    ApiResponse<AccountResponse> withdraw(
            @PathVariable String accountNumber,
            @RequestParam BigDecimal amount
    );

    @GetMapping("/accounts/customer/{customerId}")
    ApiResponse<List<AccountResponse>> getAccountsByCustomer(
            @PathVariable Long customerId
    );

}