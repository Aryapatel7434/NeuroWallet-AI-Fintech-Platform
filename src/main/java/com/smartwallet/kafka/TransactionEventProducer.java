package com.smartwallet.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartwallet.dto.TransactionEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionEventProducer {

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
            String message =
                    objectMapper.writeValueAsString(event);

            kafkaTemplate.send(TOPIC, message);

            System.out.println(
                    "Kafka event published: " + message
            );

        } catch (Exception e) {
            System.out.println(
                    "Failed to publish Kafka event: "
                            + e.getMessage()
            );
        }
    }
}