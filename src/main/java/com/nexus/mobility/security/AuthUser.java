package com.nexus.mobility.security;

import com.nexus.mobility.entity.DomainEnums;

import java.util.UUID;

public record AuthUser(
        UUID userId,
        UUID tenantId,
        String email,
        String fullName,
        DomainEnums.UserRole role
) {
}
