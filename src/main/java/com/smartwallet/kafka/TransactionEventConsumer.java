/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartwallet.kafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
/**
 *
 * @author ABC
 */
@Service//Spring automatically create object
public class TransactionEventConsumer {
    @KafkaListener(topics = "transaction-events",groupId = "neurowallet-group")
    
    public void consumeTransactionEvent(String message){
        System.out.println("Kafka event received:"+message);
        
        System.out.println("Notification can be sent from here");
    }
    
    
}
