package com.nexus.mobility.repository;

import com.nexus.mobility.entity.ProgramStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProgramStudentRepository extends JpaRepository<ProgramStudent, UUID> {
    List<ProgramStudent> findByProgramId(UUID programId);
    Optional<ProgramStudent> findByProgramIdAndStudentId(UUID programId, UUID studentId);
}
