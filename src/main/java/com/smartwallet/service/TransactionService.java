package com.smartwallet.service;

import com.smartwallet.dto.TransactionAnalyticsResponse;
import com.smartwallet.dto.TransactionEvent;
import com.smartwallet.dto.TransactionRequest;
import com.smartwallet.exception.BadRequestException;
import com.smartwallet.exception.ResourceNotFoundException;
import com.smartwallet.kafka.TransactionEventProducer;
import com.smartwallet.model.Transaction;
import com.smartwallet.model.TransactionStatus;
import com.smartwallet.model.User;
import com.smartwallet.model.Wallet;
import com.smartwallet.repository.TransactionRepository;
import com.smartwallet.repository.UserRepository;
import com.smartwallet.repository.WalletRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class TransactionService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionAuditService transactionAuditService;
    private final WalletCacheService walletCacheService;
    private final TransactionEventProducer transactionEventProducer;

    public TransactionService(
            UserRepository userRepository,
            WalletRepository walletRepository,
            TransactionRepository transactionRepository,
            TransactionAuditService transactionAuditService,
            WalletCacheService walletCacheService,
            TransactionEventProducer transactionEventProducer) {

        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.transactionAuditService = transactionAuditService;
        this.walletCacheService = walletCacheService;
        this.transactionEventProducer = transactionEventProducer;
    }

    @Transactional
    public String sendMoney(TransactionRequest request) {

        log.info("Send money request received. Receiver={}, Amount={}",
                request.getReceiverEmail());

        if (request.getAmount() == null ||
                request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {

            log.warn("Invalid transaction amount: {}", request.getAmount());
            throw new BadRequestException("Amount must be greater than zero");
        }

        String senderEmail = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        log.info("Transaction started. Sender={}, Receiver={}, Amount={}",
                senderEmail);

        User sender = userRepository.findByEmail(senderEmail);

        if (sender == null) {
            log.error("Transaction failed. Sender not found: {}", senderEmail);
            throw new ResourceNotFoundException("Sender not found");
        }

        User receiver = userRepository.findByEmail(request.getReceiverEmail());

        if (receiver == null) {

            log.error("Transaction failed. Receiver not found: {}",
                    request.getReceiverEmail()
            );

            transactionAuditService.saveFailedTransaction(
                    senderEmail,
                    request.getReceiverEmail(),
                    request.getAmount()
            );

            throw new ResourceNotFoundException("Receiver not found");
        }

        if (sender.getEmail().equals(receiver.getEmail())) {

            log.warn("Transaction rejected. Sender cannot send money to self: {}",
                    sender.getEmail()
            );

            transactionAuditService.saveFailedTransaction(
                    sender.getEmail(),
                    receiver.getEmail(),
                    request.getAmount()
            );

            throw new BadRequestException("Cannot send money to yourself");
        }

        Wallet senderWallet =
                walletRepository.findByUserEmail(sender.getEmail());

        Wallet receiverWallet =
                walletRepository.findByUserEmail(receiver.getEmail());

        if (senderWallet == null) {
            log.error("Transaction failed. Sender wallet not found: {}",
                    sender.getEmail()
            );
            throw new ResourceNotFoundException("Sender wallet not found");
        }

        if (receiverWallet == null) {
            log.error("Transaction failed. Receiver wallet not found: {}",
                    receiver.getEmail()
            );
            throw new ResourceNotFoundException("Receiver wallet not found");
        }

        if (senderWallet.getBalance().compareTo(request.getAmount()) < 0) {

            log.warn("Transaction failed due to insufficient balance. Sender={}, Balance={}, Required={}",
                    sender.getEmail());

            transactionAuditService.saveFailedTransaction(
                    sender.getEmail(),
                    receiver.getEmail(),
                    request.getAmount()
            );

            throw new BadRequestException("Insufficient balance");
        }

        Transaction transaction =
                new Transaction(
                        sender.getEmail(),
                        receiver.getEmail(),
                        request.getAmount(),
                        TransactionStatus.PENDING,
                        LocalDateTime.now()
                );

        transactionRepository.save(transaction);

        log.info("Transaction saved with PENDING status. Sender={}, Receiver={}, Amount={}",
                sender.getEmail());

        senderWallet.setBalance(
                senderWallet.getBalance().subtract(request.getAmount())
        );

        receiverWallet.setBalance(
                receiverWallet.getBalance().add(request.getAmount())
        );

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        log.info("Wallet balances updated. Sender={}, Receiver={}",
                sender.getEmail(),
                receiver.getEmail()
        );

        transaction.setStatus(TransactionStatus.SUCCESS);
        transactionRepository.save(transaction);

        log.info("Transaction status updated to SUCCESS. TransactionId={}", sender.getEmail(), receiver.getEmail());

        walletCacheService.clearWalletCache(sender.getEmail());
        walletCacheService.clearWalletCache(receiver.getEmail());

        log.info("Wallet cache cleared for sender and receiver", sender.getEmail(), receiver.getEmail());

        TransactionEvent event =
                new TransactionEvent(
                        sender.getEmail(),
                        receiver.getEmail(),
                        request.getAmount(),
                        "SUCCESS",
                        LocalDateTime.now()
                );

        transactionEventProducer.publishTransactionEvent(event);

        log.info("Kafka transaction event published. Sender={}, Receiver={}, Amount={}",
                sender.getEmail());

        return "Transaction Successful";
    }

    public Page<Transaction> getTransactionHistory(
            String email,
            int page,
            int size) {

        log.debug("Fetching transaction history. Email={}, Page={}, Size={}",
                email, page, size
        );

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by("timestamp").descending()
                );

        return transactionRepository.findBySenderEmailOrReceiverEmail(
                email,
                email,
                pageable
        );
    }

    public Page<Transaction> getSentTransactions(
            String email,
            int page,
            int size) {

        log.debug("Fetching sent transactions. Email={}, Page={}, Size={}",
                email, page, size
        );

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by("timestamp").descending()
                );

        return transactionRepository.findBySenderEmail(email, pageable);
    }

    public Page<Transaction> getReceivedTransactions(
            String email,
            int page,
            int size) {

        log.debug("Fetching received transactions. Email={}, Page={}, Size={}",
                email, page, size
        );

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by("timestamp").descending()
                );

        return transactionRepository.findByReceiverEmail(email, pageable);
    }

    public Page<Transaction> getTransactionsByStatus(
            TransactionStatus status,
            int page,
            int size) {

        log.debug("Fetching transactions by status. Status={}, Page={}, Size={}",
                status, page, size
        );

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by("timestamp").descending()
                );

        return transactionRepository.findByStatus(status, pageable);
    }

    public TransactionAnalyticsResponse getTransactionAnalytics() {

        log.debug("Fetching transaction analytics");

        long successCount =
                transactionRepository.countByStatus(
                        TransactionStatus.SUCCESS
                );

        long failedCount =
                transactionRepository.countByStatus(
                        TransactionStatus.FAILED
                );

        return new TransactionAnalyticsResponse(
                successCount,
                failedCount
        );
    }

    public Page<Transaction> searchTransactionsByEmail(
            String email,
            int page,
            int size) {

        log.debug("Searching transactions by email keyword. Email={}, Page={}, Size={}",
                email, page, size
        );

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by("timestamp").descending()
                );

        return transactionRepository
                .findBySenderEmailContainingOrReceiverEmailContaining(
                        email,
                        email,
                        pageable
                );
    }

    public Page<Transaction> getTransactionsByAmountRange(
            BigDecimal minAmount,
            BigDecimal maxAmount,
            int page,
            int size) {

        log.debug("Fetching transactions by amount range. Min={}, Max={}, Page={}, Size={}",
                minAmount, maxAmount, page, size
        );

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by("timestamp").descending()
                );

        return transactionRepository.findByAmountBetween(
                minAmount,
                maxAmount,
                pageable
        );
    }
}