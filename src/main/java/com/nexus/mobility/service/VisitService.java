package com.nexus.mobility.service;

import com.nexus.mobility.dto.ApiDtos;
import com.nexus.mobility.dto.VisitDtos;
import com.nexus.mobility.entity.Visit;
import com.nexus.mobility.entity.VisitParticipant;
import com.nexus.mobility.exception.ResourceNotFoundException;
import com.nexus.mobility.repository.VisitParticipantRepository;
import com.nexus.mobility.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class VisitService {

    private final VisitRepository visitRepository;
    private final VisitParticipantRepository visitParticipantRepository;

    public VisitService(VisitRepository visitRepository, VisitParticipantRepository visitParticipantRepository) {
        this.visitRepository = visitRepository;
        this.visitParticipantRepository = visitParticipantRepository;
    }

    public ApiDtos.PageResponse<Visit> list(UUID tenantId, String type, String status, Integer page, Integer limit) {
        List<Visit> items = visitRepository.findByTenantId(tenantId).stream()
                .filter(visit -> type == null || visit.getType().name().equalsIgnoreCase(type))
                .filter(visit -> status == null || visit.getStatus().name().equalsIgnoreCase(status))
                .sorted(Comparator.comparing(Visit::getVisitDate, Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();
        return PageMapper.page(items, page, limit);
    }

    public Visit get(UUID id) {
        return visitRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Visit not found."));
    }

    @Transactional
    public Visit create(VisitDtos.VisitRequest request) {
        Visit visit = new Visit();
        apply(visit, request);
        return visitRepository.save(visit);
    }

    @Transactional
    public Visit update(UUID id, VisitDtos.VisitRequest request) {
        Visit visit = get(id);
        apply(visit, request);
        return visitRepository.save(visit);
    }

    @Transactional
    public void delete(UUID id) {
        visitRepository.delete(get(id));
    }

    public List<VisitParticipant> participants(UUID visitId) {
        get(visitId);
        return visitParticipantRepository.findByVisitId(visitId);
    }

    @Transactional
    public List<VisitParticipant> addParticipant(UUID visitId, VisitDtos.ParticipantRequest request) {
        VisitParticipant participant = new VisitParticipant();
        participant.setVisitId(visitId);
        participant.setTenantId(request.tenantId());
        participant.setFullName(request.fullName());
        participant.setEmail(request.email());
        participant.setOrganization(request.organization());
        participant.setRole(request.role());
        visitParticipantRepository.save(participant);
        return participants(visitId);
    }

    @Transactional
    public void removeParticipant(UUID visitId, UUID participantId) {
        get(visitId);
        visitParticipantRepository.delete(visitParticipantRepository.findById(participantId)
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found.")));
    }

    private void apply(Visit visit, VisitDtos.VisitRequest request) {
        visit.setTenantId(request.tenantId());
        visit.setTitle(request.title());
        visit.setType(request.type());
        if (request.status() != null) {
            visit.setStatus(request.status());
        }
        visit.setVisitDate(request.visitDate());
        visit.setLocation(request.location());
        visit.setInstitutionName(request.institutionName());
        visit.setAgenda(request.agenda());
    }
}
