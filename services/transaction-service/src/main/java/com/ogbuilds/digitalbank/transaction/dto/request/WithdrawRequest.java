package com.ogbuilds.digitalbank.transaction.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawRequest {

    @NotBlank
    private String accountNumber;

    @NotNull
    @DecimalMin("1.00")
    private BigDecimal amount;

    private String description;

}