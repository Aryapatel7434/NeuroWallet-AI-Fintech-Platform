package com.smartwallet.repository;

import com.smartwallet.model.Transaction;
import com.smartwallet.model.TransactionStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository
        extends JpaRepository<Transaction, Integer> {

    List<Transaction> findBySenderEmailOrReceiverEmail(
            String senderEmail,
            String receiverEmail
    );

    Page<Transaction> findBySenderEmailOrReceiverEmail(
            String senderEmail,
            String receiverEmail,
            Pageable pageable
    );

    Page<Transaction> findBySenderEmail(
            String senderEmail,
            Pageable pageable
    );

    Page<Transaction> findByReceiverEmail(
            String receiverEmail,
            Pageable pageable
    );

    Page<Transaction>findByStatus(TransactionStatus status,Pageable pageable);
    long countByStatus(TransactionStatus status);
    
}