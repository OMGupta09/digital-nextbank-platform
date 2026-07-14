package com.ogbuilds.digitalbank.ai_service.dto.customer;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerResponse {

    private Long id;

    private Long authUserId;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    private String gender;

    private boolean emailVerified;

    private boolean phoneVerified;

}