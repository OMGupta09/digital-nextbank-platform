package com.ogbuilds.digitalbank.customer.service;

import com.ogbuilds.digitalbank.customer.dto.CreateCustomerRequest;
import com.ogbuilds.digitalbank.customer.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {

    CustomerResponse createCustomer(CreateCustomerRequest request);

    CustomerResponse getCustomerById(Long id);

    CustomerResponse getCustomerByAuthUserId(Long authUserId);

    CustomerResponse updateCustomer(Long id,
                                    CreateCustomerRequest request);

    void deleteCustomer(Long id);

    List<CustomerResponse> getAllCustomers();


}