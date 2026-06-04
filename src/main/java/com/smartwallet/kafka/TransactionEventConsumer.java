package com.smartwallet.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionEventConsumer {

    @KafkaListener(
            topics = "transaction-events",
            groupId = "neurowallet-group"
    )
    public void consumeTransactionEvent(String message) {

        log.info("Kafka event received: {}", message);

        log.info("Notification service triggered from Kafka consumer");
    }
}