package com.nexus.mobility.repository;

import com.nexus.mobility.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VisitRepository extends JpaRepository<Visit, UUID> {
    List<Visit> findByTenantId(UUID tenantId);
}
