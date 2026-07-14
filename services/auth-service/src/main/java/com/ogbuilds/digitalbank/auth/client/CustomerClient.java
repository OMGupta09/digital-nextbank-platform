package com.ogbuilds.digitalbank.auth.client;

import com.ogbuilds.digitalbank.auth.dto.client.ApiResponse;
import com.ogbuilds.digitalbank.auth.dto.client.CreateCustomerRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerClient {

    @PostMapping("/customers")
    ApiResponse<Object> createCustomer(@RequestBody CreateCustomerRequest request);

}