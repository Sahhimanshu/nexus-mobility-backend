package com.nexus.mobility.repository;

import com.nexus.mobility.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProgramRepository extends JpaRepository<Program, UUID> {
    List<Program> findByTenantId(UUID tenantId);
}
