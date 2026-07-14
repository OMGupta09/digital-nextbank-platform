package com.ogbuilds.digitalbank.ai_service.
        client;

import com.ogbuilds.digitalbank.ai_service.dto.common.ApiResponse;
import com.ogbuilds.digitalbank.ai_service.dto.customer.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerClient {

    @GetMapping("/customers/auth/{authUserId}")
    ApiResponse<CustomerResponse> getCustomerByAuthUserId(
            @PathVariable Long authUserId
    );

}