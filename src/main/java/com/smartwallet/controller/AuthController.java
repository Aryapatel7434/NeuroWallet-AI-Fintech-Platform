package com.smartwallet.controller;

import com.smartwallet.dto.AuthResponse;
import com.smartwallet.dto.LoginRequest;
import com.smartwallet.dto.RefreshTokenRequest;
import com.smartwallet.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(
            AuthService authService) {

        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponse login(
            @Valid @RequestBody LoginRequest request) {

        return authService.login(request);
    }

    @PostMapping("/refresh")
    public AuthResponse refreshToken(
            @RequestBody RefreshTokenRequest request) {

        return authService.refreshToken(
                request.getRefreshToken()
        );
    }
}