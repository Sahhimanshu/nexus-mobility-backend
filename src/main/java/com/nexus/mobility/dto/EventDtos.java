package com.nexus.mobility.dto;

import com.nexus.mobility.entity.DomainEnums;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public final class EventDtos {

    private EventDtos() {
    }

    public record EventRequest(
            @NotNull UUID tenantId,
            @NotBlank String name,
            @NotNull DomainEnums.EventType type,
            LocalDate eventDate,
            String countryCode,
            String location,
            String hostInstitution,
            String description
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
