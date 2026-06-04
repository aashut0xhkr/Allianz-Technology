package com.allianz.insurance.service;

import com.allianz.insurance.dto.customer.CustomerRequest;
import com.allianz.insurance.dto.customer.CustomerResponse;
import com.allianz.insurance.entity.Customer;
import com.allianz.insurance.exception.CustomerAlreadyExistsException;
import com.allianz.insurance.exception.CustomerNotFoundException;
import com.allianz.insurance.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerResponse createCustomer(
            CustomerRequest request) {

        log.info("Customer creation request received for email: {}",
                request.getEmail());

        if (customerRepository
                .findByEmail(request.getEmail())
                .isPresent()) {

            throw new CustomerAlreadyExistsException(
                    "Customer already exists with email: "
                            + request.getEmail());
        }

        Customer customer = Customer.builder()
                .customerCode(
                        "CUST-" +
                                UUID.randomUUID()
                                        .toString()
                                        .substring(0, 8))
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .dateOfBirth(request.getDateOfBirth())
                .build();

        customerRepository.save(customer);

        log.info("Customer created successfully with code: {}",
                customer.getCustomerCode());

        return CustomerResponse.builder()
                .id(customer.getId())
                .customerCode(customer.getCustomerCode())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .address(customer.getAddress())
                .build();
    }
    public CustomerResponse getCustomerById(Long id) {

        log.info("Fetching customer with id: {}", id);

        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer not found with id: " + id));

        return CustomerResponse.builder()
                .id(customer.getId())
                .customerCode(customer.getCustomerCode())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .address(customer.getAddress())
                .build();
    }
    public List<CustomerResponse> getAllCustomers() {

        log.info("Fetching all customers");

        return customerRepository.findAll()
                .stream()
                .map(customer ->
                        CustomerResponse.builder()
                                .id(customer.getId())
                                .customerCode(customer.getCustomerCode())
                                .fullName(customer.getFullName())
                                .email(customer.getEmail())
                                .phoneNumber(customer.getPhoneNumber())
                                .address(customer.getAddress())
                                .build())
                .toList();
    }
    public CustomerResponse updateCustomer(
            Long id,
            CustomerRequest request) {

        log.info("Updating customer with id: {}", id);

        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer not found with id: " + id));

        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setAddress(request.getAddress());
        customer.setDateOfBirth(request.getDateOfBirth());

        customerRepository.save(customer);

        log.info("Customer updated successfully: {}", id);

        return CustomerResponse.builder()
                .id(customer.getId())
                .customerCode(customer.getCustomerCode())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .address(customer.getAddress())
                .build();
    }
    public void deleteCustomer(Long id) {

        log.info("Deleting customer with id: {}", id);

        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer not found with id: " + id));

        customerRepository.delete(customer);

        log.info("Customer deleted successfully: {}", id);
    }
}