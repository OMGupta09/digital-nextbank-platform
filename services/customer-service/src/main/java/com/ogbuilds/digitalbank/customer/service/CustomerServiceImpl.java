package com.ogbuilds.digitalbank.customer.service;

import com.ogbuilds.digitalbank.customer.dto.CreateCustomerRequest;
import com.ogbuilds.digitalbank.customer.dto.CustomerResponse;
import com.ogbuilds.digitalbank.customer.entity.Customer;
import com.ogbuilds.digitalbank.customer.exception.CustomerNotFoundException;
import com.ogbuilds.digitalbank.customer.exception.DuplicatePhoneNumberException;
import com.ogbuilds.digitalbank.customer.mapper.CustomerMapper;
import com.ogbuilds.digitalbank.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest request) {

        if(repository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new DuplicatePhoneNumberException("Phone number already exists.");
        }

        Customer customer = mapper.toEntity(request);

        customer = repository.save(customer);

        return mapper.toResponse(customer);
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {

        Customer customer = repository.findById(id)
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found."));

        return mapper.toResponse(customer);
    }

    @Override
    public CustomerResponse getCustomerByAuthUserId(Long authUserId) {

        Customer customer = repository.findByAuthUserId(authUserId)
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found."));

        return mapper.toResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomer(Long id,
                                           CreateCustomerRequest request) {

        Customer customer = repository.findById(id)
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found."));

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setDateOfBirth(request.getDateOfBirth());
        customer.setGender(request.getGender());

        customer = repository.save(customer);

        return mapper.toResponse(customer);
    }

    @Override
    public void deleteCustomer(Long id) {

        Customer customer = repository.findById(id)
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found."));

        repository.delete(customer);

    }

    @Override
    public List<CustomerResponse> getAllCustomers() {

        return repository
                .findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();

    }

}