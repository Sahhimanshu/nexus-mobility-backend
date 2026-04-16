package com.nexus.mobility.dto;

import com.nexus.mobility.entity.DomainEnums;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public final class AuthDtos {

    private AuthDtos() {
    }

    public record LoginRequest(@Email @NotBlank String email, @NotBlank String password) {
    }

    public record RefreshRequest(@NotBlank String refreshToken) {
    }

    public record ForgotPasswordRequest(@Email @NotBlank String email) {
    }

    public record ResetPasswordRequest(@NotBlank String token, @NotBlank String newPassword) {
    }

    public record AuthResponse(
            String accessToken,
            String refreshToken,
            UUID tenantId,
            DomainEnums.UserRole role,
            UUID userId,
            String fullName
    ) {
    }

    public record MeResponse(
            UUID userId,
            UUID tenantId,
            String fullName,
            String email,
            DomainEnums.UserRole role
    ) {
    }
}
