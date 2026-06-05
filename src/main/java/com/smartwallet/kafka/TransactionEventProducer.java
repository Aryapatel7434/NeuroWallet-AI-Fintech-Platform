package com.smartwallet.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartwallet.dto.TransactionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionEventProducer {

    private static final Logger logger =
            LoggerFactory.getLogger(TransactionEventProducer.class);

    private static final String TOPIC = "transaction-events";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public TransactionEventProducer(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper) {

        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishTransactionEvent(TransactionEvent event) {

        try {
            logger.info("Preparing Kafka transaction event for publishing");

            String message =
                    objectMapper.writeValueAsString(event);

            kafkaTemplate.send(TOPIC, message);

            logger.info("Kafka event published successfully to topic: {}", TOPIC);
            logger.info("Kafka event message: {}", message);

        } catch (Exception e) {

            logger.error("Failed to publish Kafka transaction event: {}", e.getMessage());
        }
    }
}