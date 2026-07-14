package com.ogbuilds.digitalbank.ai_service.client;

import com.ogbuilds.digitalbank.ai_service.dto.account.AccountResponse;
import com.ogbuilds.digitalbank.ai_service.dto.common.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "ACCOUNT-SERVICE")
public interface AccountClient {

    @GetMapping("/accounts/me")
    ApiResponse<List<AccountResponse>> getMyAccounts(
            @RequestHeader("X-User-Id") Long authUserId
    );

}