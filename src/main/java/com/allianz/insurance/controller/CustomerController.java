package com.allianz.insurance.controller;

import com.allianz.insurance.dto.customer.CustomerRequest;
import com.allianz.insurance.dto.customer.CustomerResponse;
import com.allianz.insurance.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerResponse>
    createCustomer(
            @Valid
            @RequestBody
            CustomerRequest request) {

        log.info("Create customer API invoked");

        return ResponseEntity.ok(
                customerService
                        .createCustomer(request));
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerResponse>
    getCustomerById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                customerService.getCustomerById(id));
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CustomerResponse>>
    getAllCustomers() {

        return ResponseEntity.ok(
                customerService.getAllCustomers());
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerResponse>
    updateCustomer(
            @PathVariable Long id,
            @Valid
            @RequestBody CustomerRequest request) {

        return ResponseEntity.ok(
                customerService.updateCustomer(
                        id,
                        request));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String>
    deleteCustomer(
            @PathVariable Long id) {

        customerService.deleteCustomer(id);

        return ResponseEntity.ok(
                "Customer deleted successfully");
    }

}