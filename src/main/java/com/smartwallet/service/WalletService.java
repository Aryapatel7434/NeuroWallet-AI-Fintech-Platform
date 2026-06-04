package com.smartwallet.service;

import com.smartwallet.dto.AddMoneyRequest;
import com.smartwallet.dto.WithdrawRequest;
import com.smartwallet.exception.BadRequestException;
import com.smartwallet.exception.ResourceNotFoundException;
import com.smartwallet.model.Wallet;
import com.smartwallet.repository.UserRepository;
import com.smartwallet.repository.WalletRepository;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final WalletCacheService walletCacheService;

    public WalletService(
            WalletRepository walletRepository,
            UserRepository userRepository,
            WalletCacheService walletCacheService) {

        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
        this.walletCacheService = walletCacheService;
    }

    @Cacheable(value = "myWallet", key = "#email")
    public Wallet getWalletByEmail(String email) {

        log.info("Fetching wallet from MySQL for user: {}", email);

        Wallet wallet = walletRepository.findByUserEmail(email);

        if (wallet == null) {
            log.warn("Wallet not found for user: {}", email);
            throw new ResourceNotFoundException("Wallet not found");
        }

        return wallet;
    }

    public Wallet getMyWallet() {

        String email = getCurrentUserEmail();

        log.info("Get wallet request received for user: {}", email);

        return getWalletByEmail(email);
    }

    @Transactional
    public Wallet addMoney(AddMoneyRequest request) {

        if (request.getAmount() == null ||
                request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {

            log.warn("Invalid add money amount: {}", request.getAmount());
            throw new BadRequestException("Amount must be greater than zero");
        }

        String email = getCurrentUserEmail();

        log.info("Add money request received. User={}, Amount={}",
                email,
                request.getAmount()
        );

        Wallet wallet = walletRepository.findByUserEmail(email);

        if (wallet == null) {
            log.warn("Add money failed. Wallet not found for user: {}", email);
            throw new ResourceNotFoundException("Wallet not found");
        }

        wallet.setBalance(
                wallet.getBalance().add(request.getAmount())
        );

        Wallet savedWallet = walletRepository.save(wallet);

        walletCacheService.clearWalletCache(email);

        log.info("Money added successfully. User={}, NewBalance={}",
                email,
                savedWallet.getBalance()
        );

        return savedWallet;
    }

    @Transactional
    public Wallet withdrawMoney(WithdrawRequest request) {

        if (request.getAmount() == null ||
                request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {

            log.warn("Invalid withdraw amount: {}", request.getAmount());
            throw new BadRequestException("Withdraw amount must be greater than zero");
        }

        String email = getCurrentUserEmail();

        log.info("Withdraw request received. User={}, Amount={}",
                email,
                request.getAmount()
        );

        Wallet wallet = walletRepository.findByUserEmail(email);

        if (wallet == null) {
            log.warn("Withdraw failed. Wallet not found for user: {}", email);
            throw new ResourceNotFoundException("Wallet not found");
        }

        if (wallet.getBalance().compareTo(request.getAmount()) < 0) {

            log.warn("Withdraw failed due to insufficient balance. User={}, Balance={}, Requested={}",
                    email,
                    wallet.getBalance(),
                    request.getAmount()
            );

            throw new BadRequestException("Insufficient wallet balance");
        }

        wallet.setBalance(
                wallet.getBalance().subtract(request.getAmount())
        );

        Wallet savedWallet = walletRepository.save(wallet);

        walletCacheService.clearWalletCache(email);

        log.info("Money withdrawn successfully. User={}, RemainingBalance={}",
                email,
                savedWallet.getBalance()
        );

        return savedWallet;
    }

    public String getCurrentUserEmail() {

        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }
}