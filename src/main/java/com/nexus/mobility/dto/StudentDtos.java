package com.nexus.mobility.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public final class StudentDtos {

    private StudentDtos() {
    }

    public record StudentRequest(
            @NotNull UUID tenantId,
            @NotBlank String fullName,
            @Email @NotBlank String email,
            String homeUniversity,
            String hostUniversity,
            String hostCountryCode,
            String programName,
            String semesterLabel,
            BigDecimal gpa,
            String status
    ) {
    }
}
