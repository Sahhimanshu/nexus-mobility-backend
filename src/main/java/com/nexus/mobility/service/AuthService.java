package com.nexus.mobility.service;

import com.nexus.mobility.config.JwtProperties;
import com.nexus.mobility.dto.ApiDtos;
import com.nexus.mobility.dto.AuthDtos;
import com.nexus.mobility.entity.PasswordResetToken;
import com.nexus.mobility.entity.RefreshToken;
import com.nexus.mobility.entity.UserAccount;
import com.nexus.mobility.exception.BadRequestException;
import com.nexus.mobility.exception.ResourceNotFoundException;
import com.nexus.mobility.repository.PasswordResetTokenRepository;
import com.nexus.mobility.repository.RefreshTokenRepository;
import com.nexus.mobility.repository.UserAccountRepository;
import com.nexus.mobility.security.AuthUser;
import com.nexus.mobility.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class AuthService {

    private final UserAccountRepository userAccountRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    public AuthService(
            UserAccountRepository userAccountRepository,
            RefreshTokenRepository refreshTokenRepository,
            PasswordResetTokenRepository passwordResetTokenRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            JwtProperties jwtProperties
    ) {
        this.userAccountRepository = userAccountRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.jwtProperties = jwtProperties;
    }

    @Transactional
    public AuthDtos.AuthResponse login(AuthDtos.LoginRequest request) {
        UserAccount user = userAccountRepository.findByEmail(request.email().toLowerCase())
                .orElseThrow(() -> new BadRequestException("Invalid email or password."));
        if (!user.isActive() || !passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new BadRequestException("Invalid email or password.");
        }

        String accessToken = jwtService.generateAccessToken(user);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(user.getId());
        refreshToken.setToken(UUID.randomUUID() + "." + UUID.randomUUID());
        refreshToken.setExpiresAt(OffsetDateTime.now().plus(jwtProperties.refreshTokenDays(), ChronoUnit.DAYS));
        refreshToken.setRevoked(false);
        refreshTokenRepository.save(refreshToken);

        return new AuthDtos.AuthResponse(
                accessToken,
                refreshToken.getToken(),
                user.getTenantId(),
                user.getRole(),
                user.getId(),
                user.getFullName()
        );
    }

    @Transactional
    public AuthDtos.AuthResponse refresh(AuthDtos.RefreshRequest request) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(request.refreshToken())
                .orElseThrow(() -> new BadRequestException("Invalid refresh token."));
        if (refreshToken.isRevoked() || refreshToken.getExpiresAt().isBefore(OffsetDateTime.now())) {
            throw new BadRequestException("Refresh token expired or revoked.");
        }

        UserAccount user = userAccountRepository.findById(refreshToken.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        return new AuthDtos.AuthResponse(
                jwtService.generateAccessToken(user),
                refreshToken.getToken(),
                user.getTenantId(),
                user.getRole(),
                user.getId(),
                user.getFullName()
        );
    }

    @Transactional
    public ApiDtos.MessageResponse logout(String refreshTokenValue) {
        if (refreshTokenValue != null && !refreshTokenValue.isBlank()) {
            refreshTokenRepository.findByToken(refreshTokenValue).ifPresent(token -> {
                token.setRevoked(true);
                refreshTokenRepository.save(token);
            });
        }
        return new ApiDtos.MessageResponse("Logged out successfully.");
    }

    @Transactional
    public ApiDtos.MessageResponse forgotPassword(AuthDtos.ForgotPasswordRequest request) {
        UserAccount user = userAccountRepository.findByEmail(request.email().toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        PasswordResetToken token = new PasswordResetToken();
        token.setUserId(user.getId());
        token.setToken(UUID.randomUUID().toString());
        token.setExpiresAt(OffsetDateTime.now().plusHours(2));
        token.setUsed(false);
        passwordResetTokenRepository.save(token);
        return new ApiDtos.MessageResponse("Password reset token generated: " + token.getToken());
    }

    @Transactional
    public ApiDtos.MessageResponse resetPassword(AuthDtos.ResetPasswordRequest request) {
        PasswordResetToken token = passwordResetTokenRepository.findByToken(request.token())
                .orElseThrow(() -> new BadRequestException("Invalid reset token."));
        if (token.isUsed() || token.getExpiresAt().isBefore(OffsetDateTime.now())) {
            throw new BadRequestException("Reset token expired or already used.");
        }
        UserAccount user = userAccountRepository.findById(token.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        token.setUsed(true);
        userAccountRepository.save(user);
        passwordResetTokenRepository.save(token);
        return new ApiDtos.MessageResponse("Password updated successfully.");
    }

    public AuthDtos.MeResponse me(AuthUser authUser) {
        UserAccount user;
        if (authUser != null) {
            user = userAccountRepository.findById(authUser.userId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        } else {
            user = userAccountRepository.findAll().stream()
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("No users are available."));
        }
        return new AuthDtos.MeResponse(user.getId(), user.getTenantId(), user.getFullName(), user.getEmail(), user.getRole());
    }
}
