package com.ogbuilds.digitalbank.customer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "kyc")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Kyc extends BaseEntity {

    @Column(nullable = false, unique = true)
    private Long customerId;

    @Column(nullable = false, unique = true, length = 10)
    private String panNumber;

    @Column(nullable = false, unique = true, length = 12)
    private String aadhaarNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KycStatus status;

    private LocalDateTime verifiedAt;

}