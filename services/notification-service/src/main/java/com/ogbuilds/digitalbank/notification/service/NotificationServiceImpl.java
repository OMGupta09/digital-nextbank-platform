package com.ogbuilds.digitalbank.notification.service;

import com.ogbuilds.digitalbank.notification.dto.event.TransactionCompletedEvent;
import com.ogbuilds.digitalbank.notification.dto.event.TransactionFailedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationServiceImpl
        implements NotificationService {

    @Override
    public void handleTransactionCompleted(
            TransactionCompletedEvent event) {

        log.info("""
                
                =======================================
                TRANSACTION COMPLETED
                
                Reference : {}
                From      : {}
                To        : {}
                Amount    : {}
                Type      : {}
                Time      : {}
                =======================================
                """,
                event.getReferenceId(),
                event.getFromAccountNumber(),
                event.getToAccountNumber(),
                event.getAmount(),
                event.getTransactionType(),
                event.getCreatedAt());

    }

    @Override
    public void handleTransactionFailed(
            TransactionFailedEvent event) {

        log.error("""
                
                =======================================
                TRANSACTION FAILED
                
                Reference : {}
                From      : {}
                To        : {}
                Amount    : {}
                Reason    : {}
                Time      : {}
                =======================================
                """,
                event.getReferenceId(),
                event.getFromAccountNumber(),
                event.getToAccountNumber(),
                event.getAmount(),
                event.getReason(),
                event.getCreatedAt());

    }

}