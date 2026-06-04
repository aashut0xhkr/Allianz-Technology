package com.allianz.insurance.dto.customer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse {

    private Long id;

    private String customerCode;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String address;
}