package com.allianz.insurance.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerRequest {

    @NotBlank
    private String fullName;

    @Email
    private String email;

    @NotBlank
    private String phoneNumber;

    private String address;

    private LocalDate dateOfBirth;
}