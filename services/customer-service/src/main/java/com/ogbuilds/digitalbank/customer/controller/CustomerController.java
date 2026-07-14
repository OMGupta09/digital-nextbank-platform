package com.ogbuilds.digitalbank.customer.controller;

import com.ogbuilds.digitalbank.customer.dto.CreateCustomerRequest;
import com.ogbuilds.digitalbank.customer.dto.CustomerResponse;
import com.ogbuilds.digitalbank.customer.security.AccessGuard;
import com.ogbuilds.digitalbank.customer.service.CustomerService;
import com.ogbuilds.digitalbank.customer.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final AccessGuard accessGuard;

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(
            @Valid @RequestBody CreateCustomerRequest request) {

        CustomerResponse response = customerService.createCustomer(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CustomerResponse>builder()
                        .success(true)
                        .message("Customer created successfully.")
                        .data(response)
                        .build());

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomer(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long requesterAuthUserId,
            @RequestHeader("X-User-Role") String role) {

        CustomerResponse response = customerService.getCustomerById(id);

        accessGuard.requireSelfOrAdmin(response.getAuthUserId(), requesterAuthUserId, role);

        return ResponseEntity.ok(
                ApiResponse.<CustomerResponse>builder()
                        .success(true)
                        .message("Customer fetched successfully.")
                        .data(response)
                        .build());

    }

    @GetMapping("/auth/{authUserId}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getByAuthUserId(
            @PathVariable Long authUserId) {

        CustomerResponse response =
                customerService.getCustomerByAuthUserId(authUserId);

        return ResponseEntity.ok(
                ApiResponse.<CustomerResponse>builder()
                        .success(true)
                        .message("Customer fetched successfully.")
                        .data(response)
                        .build());

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CreateCustomerRequest request,
            @RequestHeader("X-User-Id") Long requesterAuthUserId,
            @RequestHeader("X-User-Role") String role) {

        CustomerResponse existing = customerService.getCustomerById(id);

        accessGuard.requireSelfOrAdmin(existing.getAuthUserId(), requesterAuthUserId, role);

        CustomerResponse response =
                customerService.updateCustomer(id, request);

        return ResponseEntity.ok(
                ApiResponse.<CustomerResponse>builder()
                        .success(true)
                        .message("Customer updated successfully.")
                        .data(response)
                        .build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(
            @PathVariable Long id,
            @RequestHeader("X-User-Role") String role) {

        accessGuard.requireAdmin(role);

        customerService.deleteCustomer(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Customer deleted successfully.")
                        .build());

    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getAllCustomers(
            @RequestHeader("X-User-Role") String role) {

        accessGuard.requireAdmin(role);

        List<CustomerResponse> response =
                customerService.getAllCustomers();

        return ResponseEntity.ok(
                ApiResponse.<List<CustomerResponse>>builder()
                        .success(true)
                        .message("Customers fetched successfully.")
                        .data(response)
                        .build()
        );

    }

}