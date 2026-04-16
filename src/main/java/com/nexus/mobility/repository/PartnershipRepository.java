package com.nexus.mobility.repository;

import com.nexus.mobility.entity.Partnership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PartnershipRepository extends JpaRepository<Partnership, UUID> {
    List<Partnership> findByTenantId(UUID tenantId);
}
