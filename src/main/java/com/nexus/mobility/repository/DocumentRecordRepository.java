package com.nexus.mobility.repository;

import com.nexus.mobility.entity.DocumentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DocumentRecordRepository extends JpaRepository<DocumentRecord, UUID> {
    List<DocumentRecord> findByTenantId(UUID tenantId);
}
