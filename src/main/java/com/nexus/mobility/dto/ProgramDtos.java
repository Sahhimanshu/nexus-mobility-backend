package com.nexus.mobility.dto;

import com.nexus.mobility.entity.DomainEnums;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public final class ProgramDtos {

    private ProgramDtos() {
    }

    public record ProgramRequest(
            @NotNull UUID tenantId,
            UUID partnershipId,
            @NotBlank String name,
            @NotNull DomainEnums.ProgramType type,
            String partnerUniversity,
            String countryCode,
            Integer seats,
            Integer enrolled,
            LocalDate deadline,
            String durationLabel,
            Boolean scholarshipAvailable,
            String description
    ) {
    }

    public record ProgramStudentRequest(@NotNull UUID studentId) {
    }

    public record ColumnRequest(
            UUID id,
            @NotNull UUID tenantId,
            @NotBlank String keyName,
            @NotBlank String label,
            @NotBlank String valueType,
            Integer sortOrder,
            Boolean visible
    ) {
    }

    public record FieldRequest(
            UUID id,
            @NotNull UUID tenantId,
            @NotBlank String keyName,
            @NotBlank String label,
            @NotBlank String inputType,
            Boolean requiredField,
            Integer sortOrder
    ) {
    }
}
