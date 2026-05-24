package com.smartwallet.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class ScheduledTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduledTransactionId;

    private String senderEmail;

    private String receiverEmail;

    private BigDecimal amount;

    private LocalDateTime scheduledTime;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    public ScheduledTransaction() {
    }

    public ScheduledTransaction(
            String senderEmail,
            String receiverEmail,
            BigDecimal amount,
            LocalDateTime scheduledTime,
            TransactionStatus status) {

        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.amount = amount;
        this.scheduledTime = scheduledTime;
        this.status = status;
    }

    public Long getScheduledTransactionId() {
        return scheduledTransactionId;
    }

    public void setScheduledTransactionId(Long scheduledTransactionId) {
        this.scheduledTransactionId = scheduledTransactionId;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}