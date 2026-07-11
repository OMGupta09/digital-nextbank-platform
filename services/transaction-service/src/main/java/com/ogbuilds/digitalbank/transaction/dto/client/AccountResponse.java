package com.ogbuilds.digitalbank.transaction.dto.client;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

    private Long id;

    private String accountNumber;

    private Long customerId;

    private String accountType;

    private String status;

    private BigDecimal balance;

    private String currency;

}