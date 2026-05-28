package com.smartwallet.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class WalletCacheService {

    @CacheEvict(value = "myWallet", key = "#email")
    public void clearWalletCache(String email) {
        System.out.println("Wallet cache cleared for: " + email);
    }
}