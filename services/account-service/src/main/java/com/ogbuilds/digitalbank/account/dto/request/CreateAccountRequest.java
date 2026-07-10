package com.ogbuilds.digitalbank.account.dto.request;

import com.ogbuilds.digitalbank.account.enums.AccountType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountRequest {

    @NotNull
    private Long customerId;

    @NotNull
    private AccountType accountType;

    @DecimalMin(value = "0.00")
    private BigDecimal initialDeposit;

}