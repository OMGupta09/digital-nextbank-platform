package com.ogbuilds.digitalbank.notification.service;

import com.ogbuilds.digitalbank.notification.dto.event.TransactionCompletedEvent;
import com.ogbuilds.digitalbank.notification.dto.event.TransactionFailedEvent;

public interface NotificationService {

    void handleTransactionCompleted(
            TransactionCompletedEvent event
    );

    void handleTransactionFailed(
            TransactionFailedEvent event
    );

}