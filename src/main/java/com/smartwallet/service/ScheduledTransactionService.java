package com.smartwallet.service;

import com.smartwallet.dto.ScheduleTransactionRequest;
import com.smartwallet.model.ScheduledTransaction;
import com.smartwallet.model.TransactionStatus;
import com.smartwallet.repository.ScheduledTransactionRepository;
import java.time.LocalDateTime;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service

//This service Responsible for Scheduling Future Transactions
public class ScheduledTransactionService {

    private final ScheduledTransactionRepository repository;//Used for Database Operations

    
    //constructor injection //spring automatically inject repostiry object
    public ScheduledTransactionService(
            ScheduledTransactionRepository repository) {

        this.repository = repository;
    }
    //Main Method
    public String scheduleTransaction(
            ScheduleTransactionRequest request) {//input DTO
       //Get logged in user
        String senderEmail = SecurityContextHolder
                .getContext()//logged in user Email
                .getAuthentication()
                .getName();
        //Validate Scheduled Time
        if (request.getScheduledTime()
                .isBefore(LocalDateTime.now())) {

            return "Scheduled time must be future time";
        }
        //Create ScheduleTransactio object
        ScheduledTransaction transaction =
                new ScheduledTransaction(
                        senderEmail,
                        request.getReceiverEmail(),
                        request.getAmount(),
                        request.getScheduledTime(),
                        TransactionStatus.PENDING
                );

        repository.save(transaction);

        return "Transaction Scheduled Successfully";
    }
}