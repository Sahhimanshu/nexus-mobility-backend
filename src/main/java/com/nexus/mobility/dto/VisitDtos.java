package com.nexus.mobility.dto;

import com.nexus.mobility.entity.DomainEnums;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public final class VisitDtos {

    private VisitDtos() {
    }

    public record VisitRequest(
            @NotNull UUID tenantId,
            @NotBlank String title,
            @NotNull DomainEnums.VisitType type,
            DomainEnums.VisitStatus status,
            LocalDate visitDate,
            String location,
            String institutionName,
            String agenda
    ) {
    }

    public record ParticipantRequest(
            @NotNull UUID tenantId,
            @NotBlank String fullName,
            String email,
            String organization,
            String role
    ) {
    }
}
