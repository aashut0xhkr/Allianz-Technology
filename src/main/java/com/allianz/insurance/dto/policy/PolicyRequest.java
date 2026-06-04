package com.allianz.insurance.dto.policy;

import com.allianz.insurance.enums.PolicyType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PolicyRequest {

    @NotNull
    private Long customerId;

    @NotNull
    private PolicyType policyType;

    @NotNull
    private BigDecimal premiumAmount;

    @NotNull
    private BigDecimal coverageAmount;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;
}