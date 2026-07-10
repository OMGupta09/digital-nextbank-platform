package com.ogbuilds.digitalbank.customer.mapper;

import com.ogbuilds.digitalbank.customer.dto.CreateCustomerRequest;
import com.ogbuilds.digitalbank.customer.dto.CustomerResponse;
import com.ogbuilds.digitalbank.customer.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toEntity(CreateCustomerRequest request){

        return Customer.builder()

                .authUserId(request.getAuthUserId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .emailVerified(false)
                .phoneVerified(false)
                .build();

    }

    public CustomerResponse toResponse(Customer customer){

        return CustomerResponse.builder()

                .id(customer.getId())
                .authUserId(customer.getAuthUserId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .dateOfBirth(customer.getDateOfBirth())
                .gender(customer.getGender())
                .emailVerified(customer.isEmailVerified())
                .phoneVerified(customer.isPhoneVerified())
                .build();

    }

}