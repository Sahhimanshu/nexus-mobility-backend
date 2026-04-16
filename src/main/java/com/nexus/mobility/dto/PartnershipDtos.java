package com.nexus.mobility.dto;

import com.nexus.mobility.entity.DomainEnums;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public final class PartnershipDtos {

    private PartnershipDtos() {
    }

    public record PartnershipRequest(
            @NotNull UUID tenantId,
            @NotBlank String universityName,
            @NotBlank String countryCode,
            String partnershipType,
            DomainEnums.PartnershipStatus status,
            LocalDate startDate,
            LocalDate expiryDate,
            Boolean mouSigned,
            Integer renewalAlertDays,
            String notes
    ) {
    }
}
