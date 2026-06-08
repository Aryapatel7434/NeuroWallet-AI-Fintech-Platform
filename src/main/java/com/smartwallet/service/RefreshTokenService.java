package com.smartwallet.service;

import com.smartwallet.model.RefreshToken;
import com.smartwallet.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository) {

        this.refreshTokenRepository =
                refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(
            String email) {

        RefreshToken refreshToken =
                new RefreshToken();

        refreshToken.setEmail(email);

        refreshToken.setToken(
                UUID.randomUUID().toString()
        );

        refreshToken.setExpiryDate(
                LocalDateTime.now().plusDays(7)
        );

        return refreshTokenRepository.save(
                refreshToken
        );
    }

    public RefreshToken findByToken(
            String token) {

        return refreshTokenRepository
                .findByToken(token);
    }

    public RefreshToken validateRefreshToken(
            String token) {

        RefreshToken refreshToken =
                refreshTokenRepository
                        .findByToken(token);

        if (refreshToken == null) {
            return null;
        }

        if (refreshToken.getExpiryDate()
                .isBefore(LocalDateTime.now())) {

            return null;
        }

        return refreshToken;
    }
}