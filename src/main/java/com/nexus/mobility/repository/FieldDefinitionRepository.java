package com.nexus.mobility.repository;

import com.nexus.mobility.entity.FieldDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FieldDefinitionRepository extends JpaRepository<FieldDefinition, UUID> {
    List<FieldDefinition> findByTenantIdOrderBySortOrderAsc(UUID tenantId);
}
