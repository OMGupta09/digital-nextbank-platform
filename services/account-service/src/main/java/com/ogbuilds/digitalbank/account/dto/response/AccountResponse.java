package com.ogbuilds.digitalbank.account.dto.response;

import com.ogbuilds.digitalbank.account.enums.AccountStatus;
import com.ogbuilds.digitalbank.account.enums.AccountType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {

    private Long id;

    private String accountNumber;

    private Long customerId;

    private AccountType accountType;

    private AccountStatus status;

    private BigDecimal balance;

    private String currency;

}