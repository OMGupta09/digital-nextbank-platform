package com.ogbuilds.digitalbank.transaction.producer;

import com.ogbuilds.digitalbank.transaction.dto.event.TransactionCompletedEvent;
import com.ogbuilds.digitalbank.transaction.dto.event.TransactionFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCompleted(
            TransactionCompletedEvent event) {

        kafkaTemplate.send(
                "transaction-completed",
                event
        );

        log.info(
                "Published TransactionCompletedEvent : {}",
                event.getReferenceId()
        );

    }

    public void publishFailed(
            TransactionFailedEvent event) {

        kafkaTemplate.send(
                "transaction-failed",
                event
        );


        log.info(
                "Published TransactionFailedEvent : {}",
                event.getReferenceId()
        );

    }

}