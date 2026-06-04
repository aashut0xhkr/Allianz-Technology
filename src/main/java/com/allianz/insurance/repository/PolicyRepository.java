package com.allianz.insurance.repository;

import com.allianz.insurance.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PolicyRepository extends JpaRepository<Policy, Long> {

    Optional<Policy> findByPolicyNumber(
            String policyNumber);
    List<Policy> findByCustomerId(Long customerId);
}