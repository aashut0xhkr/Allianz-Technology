package com.allianz.insurance.dto.policy;

import com.allianz.insurance.enums.PolicyStatus;
import com.allianz.insurance.enums.PolicyType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PolicyResponse {

    private Long id;

    private String policyNumber;

    private String customerCode;

    private PolicyType policyType;

    private PolicyStatus status;

    private BigDecimal premiumAmount;

    private BigDecimal coverageAmount;
}