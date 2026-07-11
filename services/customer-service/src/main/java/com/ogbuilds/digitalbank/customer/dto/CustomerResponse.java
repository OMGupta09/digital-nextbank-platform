package com.ogbuilds.digitalbank.customer.dto;

import com.ogbuilds.digitalbank.customer.entity.Gender;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CustomerResponse {

    private Long id;

    private Long authUserId;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    private Gender gender;

    private boolean emailVerified;

    private boolean phoneVerified;

}