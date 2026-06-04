package com.smartwallet.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionEventConsumer {

    @KafkaListener(
            topics = "transaction-events",
            groupId = "neurowallet-group"
    )
    public void consumeTransactionEvent(String message) {

        System.out.println("Kafka event received: " + message);

        System.out.println("Notification service triggered");
    }
}