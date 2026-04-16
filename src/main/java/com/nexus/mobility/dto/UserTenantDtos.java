package com.nexus.mobility.dto;

import com.nexus.mobility.entity.DomainEnums;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public final class UserTenantDtos {

    private UserTenantDtos() {
    }

    public record UserRequest(
            @NotNull UUID tenantId,
            @NotBlank String fullName,
            @Email @NotBlank String email,
            String password,
            @NotNull DomainEnums.UserRole role,
            Boolean active
    ) {
    }

    public record TenantSettingsRequest(
            @NotNull UUID tenantId,
            String timezone,
            String currency,
            String locale,
            String contactEmail,
            String brandingColor
    ) {
    }
}
