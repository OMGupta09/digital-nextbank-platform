package com.ogbuilds.digitalbank.notification.consumer;

import com.ogbuilds.digitalbank.notification.dto.event.TransactionCompletedEvent;
import com.ogbuilds.digitalbank.notification.dto.event.TransactionFailedEvent;
import com.ogbuilds.digitalbank.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionEventConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "transaction-completed",
            groupId = "notification-group"
    )
    public void consumeCompleted(
            TransactionCompletedEvent event) {

        notificationService.handleTransactionCompleted(event);

    }

    @KafkaListener(
            topics = "transaction-failed",
            groupId = "notification-group"
    )
    public void consumeFailed(
            TransactionFailedEvent event) {

        notificationService.handleTransactionFailed(event);

    }

}