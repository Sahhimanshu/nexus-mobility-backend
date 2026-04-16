package com.nexus.mobility.repository;

import com.nexus.mobility.entity.ColumnDefinition;
import com.nexus.mobility.entity.DomainEnums;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ColumnDefinitionRepository extends JpaRepository<ColumnDefinition, UUID> {
    List<ColumnDefinition> findByTenantIdAndModuleOrderBySortOrderAsc(UUID tenantId, DomainEnums.DefinitionModule module);
}
