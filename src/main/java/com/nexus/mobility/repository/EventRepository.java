package com.nexus.mobility.repository;

import com.nexus.mobility.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    List<Event> findByTenantId(UUID tenantId);
}
