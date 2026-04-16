package com.nexus.mobility.repository;

import com.nexus.mobility.entity.VisitParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VisitParticipantRepository extends JpaRepository<VisitParticipant, UUID> {
    List<VisitParticipant> findByVisitId(UUID visitId);
}
