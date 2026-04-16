package com.nexus.mobility.controller;

import com.nexus.mobility.dto.ProgramDtos;
import com.nexus.mobility.dto.VisitDtos;
import com.nexus.mobility.entity.ColumnDefinition;
import com.nexus.mobility.entity.DomainEnums;
import com.nexus.mobility.entity.Visit;
import com.nexus.mobility.entity.VisitParticipant;
import com.nexus.mobility.service.DefinitionService;
import com.nexus.mobility.service.VisitService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping({"/api/visits", "/api/v1/visits"})
public class VisitController {

    private final VisitService visitService;
    private final DefinitionService definitionService;

    public VisitController(VisitService visitService, DefinitionService definitionService) {
        this.visitService = visitService;
        this.definitionService = definitionService;
    }

    @GetMapping
    public Object list(@RequestParam UUID tenantId,
                       @RequestParam(required = false) String type,
                       @RequestParam(required = false) String status,
                       @RequestParam(required = false) Integer page,
                       @RequestParam(required = false) Integer limit) {
        return visitService.list(tenantId, type, status, page, limit);
    }

    @PostMapping
    public Visit create(@Valid @RequestBody VisitDtos.VisitRequest request) {
        return visitService.create(request);
    }

    @GetMapping("/{id}")
    public Visit get(@PathVariable UUID id) {
        return visitService.get(id);
    }

    @PatchMapping("/{id}")
    @PutMapping("/{id}")
    public Visit update(@PathVariable UUID id, @Valid @RequestBody VisitDtos.VisitRequest request) {
        return visitService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        visitService.delete(id);
    }

    @GetMapping("/{id}/participants")
    public List<VisitParticipant> participants(@PathVariable UUID id) {
        return visitService.participants(id);
    }

    @PostMapping("/{id}/participants")
    public List<VisitParticipant> addParticipant(@PathVariable UUID id, @Valid @RequestBody VisitDtos.ParticipantRequest request) {
        return visitService.addParticipant(id, request);
    }

    @DeleteMapping("/{id}/participants/{participantId}")
    public void removeParticipant(@PathVariable UUID id, @PathVariable UUID participantId) {
        visitService.removeParticipant(id, participantId);
    }

    @GetMapping("/columns")
    public List<ColumnDefinition> getColumns(@RequestParam UUID tenantId) {
        return definitionService.getColumns(tenantId, DomainEnums.DefinitionModule.VISIT);
    }

    @PatchMapping("/columns")
    public List<ColumnDefinition> upsertColumns(@Valid @RequestBody List<ProgramDtos.ColumnRequest> requests) {
        return definitionService.upsertColumns(DomainEnums.DefinitionModule.VISIT, requests);
    }

    @PostMapping("/columns")
    public ColumnDefinition createColumn(@Valid @RequestBody ProgramDtos.ColumnRequest request) {
        return definitionService.createColumn(DomainEnums.DefinitionModule.VISIT, request);
    }

    @DeleteMapping("/columns/{id}")
    public void deleteColumn(@PathVariable UUID id) {
        definitionService.deleteColumn(id);
    }
}
