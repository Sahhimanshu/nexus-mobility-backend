package com.nexus.mobility.repository;

import com.nexus.mobility.entity.NewsItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NewsItemRepository extends JpaRepository<NewsItem, UUID> {
    List<NewsItem> findByTenantId(UUID tenantId);
}
