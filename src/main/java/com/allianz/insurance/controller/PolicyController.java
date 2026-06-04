package com.allianz.insurance.controller;

import com.allianz.insurance.dto.policy.PolicyRequest;
import com.allianz.insurance.dto.policy.PolicyResponse;
import com.allianz.insurance.enums.PolicyStatus;
import com.allianz.insurance.service.PolicyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
@RequiredArgsConstructor
@Slf4j
public class PolicyController {

    private final PolicyService policyService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PolicyResponse>
    createPolicy(
            @Valid
            @RequestBody
            PolicyRequest request) {

        log.info("Create policy API invoked");

        return ResponseEntity.ok(
                policyService.createPolicy(request));
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PolicyResponse>
    getPolicyById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                policyService.getPolicyById(id));
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PolicyResponse>>
    getAllPolicies() {

        return ResponseEntity.ok(
                policyService.getAllPolicies());
    }
    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PolicyResponse>>
    getPoliciesByCustomer(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                policyService.getPoliciesByCustomer(
                        customerId));
    }
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PolicyResponse>
    updatePolicyStatus(
            @PathVariable Long id,
            @RequestParam PolicyStatus status) {

        return ResponseEntity.ok(
                policyService.updatePolicyStatus(
                        id,
                        status));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String>
    deletePolicy(
            @PathVariable Long id) {

        policyService.deletePolicy(id);

        return ResponseEntity.ok(
                "Policy deleted successfully");
    }
}