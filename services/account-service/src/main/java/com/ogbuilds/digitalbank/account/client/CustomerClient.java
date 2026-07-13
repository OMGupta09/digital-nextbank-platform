package com.ogbuilds.digitalbank.account.client;

import com.ogbuilds.digitalbank.account.dto.client.ApiResponse;
import com.ogbuilds.digitalbank.account.dto.client.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerClient {

    @GetMapping("/customers/{id}")
    ApiResponse<CustomerResponse> getCustomer(
            @PathVariable Long id
    );

    @GetMapping("/customers/auth/{authUserId}")
    ApiResponse<CustomerResponse> getCustomerByAuthUserId(
            @PathVariable Long authUserId
    );

}