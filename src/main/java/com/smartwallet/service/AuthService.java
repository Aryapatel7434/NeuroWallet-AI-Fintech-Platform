package com.smartwallet.service;

import com.smartwallet.dto.AuthResponse;
import com.smartwallet.dto.LoginRequest;
import com.smartwallet.model.RefreshToken;
import com.smartwallet.model.User;
import com.smartwallet.repository.UserRepository;
import com.smartwallet.security.JwtUtil;
import com.smartwallet.security.LoginAttemptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger logger =
            LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final LoginAttemptService loginAttemptService;
    private final AuditService auditService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(UserRepository userRepository,
                       JwtUtil jwtUtil,
                       BCryptPasswordEncoder passwordEncoder,
                       LoginAttemptService loginAttemptService,
                       AuditService auditService,
                       RefreshTokenService refreshTokenService) {

        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.loginAttemptService = loginAttemptService;
        this.auditService = auditService;
        this.refreshTokenService = refreshTokenService;
    }

    public AuthResponse login(LoginRequest request) {

        logger.info(
                "Login request received for email: {}",
                request.getEmail()
        );

        if (loginAttemptService.isBlocked(request.getEmail())) {

            logger.warn(
                    "Login blocked due to too many failed attempts for email: {}",
                    request.getEmail()
            );

            auditService.log(
                    request.getEmail(),
                    "LOGIN",
                    "BLOCKED"
            );

            throw new RuntimeException(
                    "Account temporarily locked due to too many failed login attempts"
            );
        }

        User user =
                userRepository.findByEmail(
                        request.getEmail()
                );

        if (user == null) {

            logger.warn(
                    "Login failed. Invalid email: {}",
                    request.getEmail()
            );

            loginAttemptService.loginFailed(
                    request.getEmail()
            );

            auditService.log(
                    request.getEmail(),
                    "LOGIN",
                    "FAILED"
            );

            throw new RuntimeException(
                    "Invalid email"
            );
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            logger.warn(
                    "Login failed. Invalid password for email: {}",
                    request.getEmail()
            );

            loginAttemptService.loginFailed(
                    request.getEmail()
            );

            auditService.log(
                    request.getEmail(),
                    "LOGIN",
                    "FAILED"
            );

            throw new RuntimeException(
                    "Invalid password"
            );
        }

        loginAttemptService.loginSucceeded(
                request.getEmail()
        );

        auditService.log(
                request.getEmail(),
                "LOGIN",
                "SUCCESS"
        );

        logger.info(
                "Login successful for email: {}",
                request.getEmail()
        );

        String accessToken =
                jwtUtil.generateToken(
                        user.getEmail(),
                        user.getRole()
                );

        RefreshToken refreshToken =
                refreshTokenService
                        .createRefreshToken(
                                user.getEmail()
                        );

        return new AuthResponse(
                accessToken,
                refreshToken.getToken()
        );
    }
}