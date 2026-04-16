package com.nexus.mobility.repository;

import com.nexus.mobility.entity.DashboardSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface DashboardSnapshotRepository extends JpaRepository<DashboardSnapshot, UUID> {
    Optional<DashboardSnapshot> findByTenantIdAndSnapshotDate(UUID tenantId, LocalDate snapshotDate);
}
