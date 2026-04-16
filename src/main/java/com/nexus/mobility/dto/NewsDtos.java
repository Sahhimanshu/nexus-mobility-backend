package com.nexus.mobility.dto;

import com.nexus.mobility.entity.DomainEnums;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public final class NewsDtos {

    private NewsDtos() {
    }

    public record NewsRequest(
            @NotNull UUID tenantId,
            @NotBlank String title,
            @NotNull DomainEnums.NewsCategory category,
            LocalDate publishDate,
            String sourceUrl,
            String summary
    ) {
    }
}
