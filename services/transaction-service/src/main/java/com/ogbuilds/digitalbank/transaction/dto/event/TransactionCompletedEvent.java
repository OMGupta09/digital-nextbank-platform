package com.ogbuilds.digitalbank.transaction.dto.event;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCompletedEvent {

    private String referenceId;

    private String fromAccountNumber;

    private String toAccountNumber;

    private BigDecimal amount;

    private String transactionType;

    private LocalDateTime createdAt;

}