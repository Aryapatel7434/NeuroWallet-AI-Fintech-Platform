package com.smartwallet.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionEventConsumer {

    private static final Logger logger =
            LoggerFactory.getLogger(TransactionEventConsumer.class);

    @KafkaListener(
            topics = "transaction-events",
            groupId = "neurowallet-group"
    )
    public void consumeTransactionEvent(String message) {

        logger.info("Kafka event received from transaction-events topic");
        logger.info("Received Kafka message: {}", message);

        logger.info("Notification service processed transaction event successfully");
    }
}