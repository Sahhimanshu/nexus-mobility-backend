package com.nexus.mobility.repository;

import com.nexus.mobility.entity.CountryStat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CountryStatRepository extends JpaRepository<CountryStat, UUID> {
    List<CountryStat> findByTenantId(UUID tenantId);
    List<CountryStat> findByTenantIdAndSnapshotYear(UUID tenantId, Integer snapshotYear);
    Optional<CountryStat> findByTenantIdAndCountryCode(UUID tenantId, String countryCode);
}
