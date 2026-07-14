package com.ogbuilds.digitalbank.ai_service.client;

import com.ogbuilds.digitalbank.ai_service.dto.common.ApiResponse;
import com.ogbuilds.digitalbank.ai_service.dto.TransactionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "TRANSACTION-SERVICE")
public interface TransactionClient {

    @GetMapping("/transactions/me")
    ApiResponse<List<TransactionResponse>> getMyTransactions(
            @RequestHeader("X-User-Id") Long authUserId
    );

}