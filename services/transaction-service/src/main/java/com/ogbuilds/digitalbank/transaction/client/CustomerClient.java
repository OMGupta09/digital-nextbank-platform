package com.ogbuilds.digitalbank.transaction.client;

import com.ogbuilds.digitalbank.transaction.dto.client.ApiResponse;
import com.ogbuilds.digitalbank.transaction.dto.client.CustomerResponse;
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