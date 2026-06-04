package com.allianz.insurance.service;

import com.allianz.insurance.dto.policy.PolicyRequest;
import com.allianz.insurance.dto.policy.PolicyResponse;
import com.allianz.insurance.entity.Customer;
import com.allianz.insurance.entity.Policy;
import com.allianz.insurance.enums.PolicyStatus;
import com.allianz.insurance.exception.CustomerNotFoundException;
import com.allianz.insurance.exception.PolicyNotFoundException;
import com.allianz.insurance.repository.CustomerRepository;
import com.allianz.insurance.repository.PolicyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final CustomerRepository customerRepository;

    public PolicyResponse createPolicy(PolicyRequest request) {
        log.info("Policy creation request received");

        Customer customer = customerRepository.findById(
                                request.getCustomerId()).orElseThrow(() ->
                                new CustomerNotFoundException("Customer not found with id : " + request.getCustomerId()));

        Policy policy = Policy.builder()
                .policyNumber(
                        "POL-" +
                                UUID.randomUUID()
                                        .toString()
                                        .substring(0, 8))
                .policyType(request.getPolicyType())
                .status(PolicyStatus.ACTIVE)
                .premiumAmount(request.getPremiumAmount())
                .coverageAmount(request.getCoverageAmount())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .customer(customer)
                .build();

        policyRepository.save(policy);

        log.info("Policy created successfully : {}",
                policy.getPolicyNumber());

        return PolicyResponse.builder()
                .id(policy.getId())
                .policyNumber(policy.getPolicyNumber())
                .customerCode(customer.getCustomerCode())
                .policyType(policy.getPolicyType())
                .status(policy.getStatus())
                .premiumAmount(policy.getPremiumAmount())
                .coverageAmount(policy.getCoverageAmount())
                .build();
    }
    public PolicyResponse getPolicyById(Long id) {

        log.info("Fetching policy with id: {}", id);

        Policy policy = policyRepository
                .findById(id)
                .orElseThrow(() ->
                        new PolicyNotFoundException(
                                "Policy not found with id: " + id));

        return PolicyResponse.builder()
                .id(policy.getId())
                .policyNumber(policy.getPolicyNumber())
                .customerCode(policy.getCustomer().getCustomerCode())
                .policyType(policy.getPolicyType())
                .status(policy.getStatus())
                .premiumAmount(policy.getPremiumAmount())
                .coverageAmount(policy.getCoverageAmount())
                .build();
    }
    public List<PolicyResponse> getAllPolicies() {

        log.info("Fetching all policies");

        return policyRepository.findAll()
                .stream()
                .map(policy ->
                        PolicyResponse.builder()
                                .id(policy.getId())
                                .policyNumber(policy.getPolicyNumber())
                                .customerCode(
                                        policy.getCustomer()
                                                .getCustomerCode())
                                .policyType(policy.getPolicyType())
                                .status(policy.getStatus())
                                .premiumAmount(policy.getPremiumAmount())
                                .coverageAmount(policy.getCoverageAmount())
                                .build())
                .toList();
    }
    public List<PolicyResponse> getPoliciesByCustomer(
            Long customerId) {

        log.info("Fetching policies for customer: {}",
                customerId);

        return policyRepository
                .findByCustomerId(customerId)
                .stream()
                .map(policy ->
                        PolicyResponse.builder()
                                .id(policy.getId())
                                .policyNumber(policy.getPolicyNumber())
                                .customerCode(
                                        policy.getCustomer()
                                                .getCustomerCode())
                                .policyType(policy.getPolicyType())
                                .status(policy.getStatus())
                                .premiumAmount(policy.getPremiumAmount())
                                .coverageAmount(policy.getCoverageAmount())
                                .build())
                .toList();
    }
    public PolicyResponse updatePolicyStatus(
            Long id,
            PolicyStatus status) {

        log.info("Updating policy status for id: {}",
                id);

        Policy policy = policyRepository
                .findById(id)
                .orElseThrow(() ->
                        new PolicyNotFoundException(
                                "Policy not found with id: " + id));

        policy.setStatus(status);

        policyRepository.save(policy);

        return PolicyResponse.builder()
                .id(policy.getId())
                .policyNumber(policy.getPolicyNumber())
                .customerCode(
                        policy.getCustomer()
                                .getCustomerCode())
                .policyType(policy.getPolicyType())
                .status(policy.getStatus())
                .premiumAmount(policy.getPremiumAmount())
                .coverageAmount(policy.getCoverageAmount())
                .build();
    }
    public void deletePolicy(Long id) {

        log.info("Deleting policy with id: {}", id);

        Policy policy = policyRepository
                .findById(id)
                .orElseThrow(() ->
                        new PolicyNotFoundException(
                                "Policy not found with id: " + id));

        policyRepository.delete(policy);

        log.info("Policy deleted successfully");
    }
}