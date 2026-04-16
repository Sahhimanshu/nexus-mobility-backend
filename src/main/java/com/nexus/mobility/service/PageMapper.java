package com.nexus.mobility.service;

import com.nexus.mobility.dto.ApiDtos;

import java.util.Collections;
import java.util.List;

public final class PageMapper {

    private PageMapper() {
    }

    public static <T> ApiDtos.PageResponse<T> page(List<T> items, Integer page, Integer limit) {
        int safePage = page == null || page < 1 ? 1 : page;
        int safeLimit = limit == null || limit < 1 ? 10 : limit;
        int fromIndex = Math.min((safePage - 1) * safeLimit, items.size());
        int toIndex = Math.min(fromIndex + safeLimit, items.size());
        List<T> content = fromIndex >= toIndex ? Collections.emptyList() : items.subList(fromIndex, toIndex);
        int totalPages = items.isEmpty() ? 0 : (int) Math.ceil((double) items.size() / safeLimit);
        return new ApiDtos.PageResponse<>(content, items.size(), totalPages, safePage, safeLimit);
    }
}
