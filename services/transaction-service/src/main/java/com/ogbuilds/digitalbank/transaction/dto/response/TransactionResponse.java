package com.ogbuilds.digitalbank.transaction.dto.response;

import com.ogbuilds.digitalbank.transaction.entity.TransactionStatus;
import com.ogbuilds.digitalbank.transaction.entity.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private Long id;

    private String referenceId;

    private String fromAccountNumber;

    private String toAccountNumber;

    private BigDecimal amount;

    private TransactionType transactionType;

    private TransactionStatus status;

    private String description;

    private LocalDateTime createdAt;

}