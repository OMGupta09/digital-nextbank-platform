package com.ogbuilds.digitalbank.ai_service.dto.account;

import com.ogbuilds.digitalbank.ai_service.dto.AccountStatus;
import com.ogbuilds.digitalbank.ai_service.dto.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
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