package com.nexus.mobility.dto;

import java.util.List;

public final class ApiDtos {

    private ApiDtos() {
    }

    public record PageResponse<T>(
            List<T> content,
            long totalElements,
            int totalPages,
            int page,
            int limit
    ) {
    }

    public record MessageResponse(String message) {
    }
}
