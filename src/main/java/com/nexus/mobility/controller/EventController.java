package com.nexus.mobility.controller;

import com.nexus.mobility.dto.EventDtos;
import com.nexus.mobility.dto.ProgramDtos;
import com.nexus.mobility.entity.ColumnDefinition;
import com.nexus.mobility.entity.DomainEnums;
import com.nexus.mobility.entity.Event;
import com.nexus.mobility.entity.EventParticipant;
import com.nexus.mobility.service.DefinitionService;
import com.nexus.mobility.service.EventService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
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

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping({"/api/events", "/api/v1/events"})
public class EventController {

    private final EventService eventService;
    private final DefinitionService definitionService;

    public EventController(EventService eventService, DefinitionService definitionService) {
        this.eventService = eventService;
        this.definitionService = definitionService;
    }

    @GetMapping
    public Object list(@RequestParam UUID tenantId,
                       @RequestParam(required = false) String type,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                       @RequestParam(required = false) Integer page,
                       @RequestParam(required = false) Integer limit) {
        return eventService.list(tenantId, type, from, to, page, limit);
    }

    @PostMapping
    public Event create(@Valid @RequestBody EventDtos.EventRequest request) {
        return eventService.create(request);
    }

    @GetMapping("/{id}")
    public Event get(@PathVariable UUID id) {
        return eventService.get(id);
    }

    @PatchMapping("/{id}")
    @PutMapping("/{id}")
    public Event update(@PathVariable UUID id, @Valid @RequestBody EventDtos.EventRequest request) {
        return eventService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        eventService.delete(id);
    }

    @GetMapping("/{id}/participants")
    public List<EventParticipant> participants(@PathVariable UUID id) {
        return eventService.participants(id);
    }

    @PostMapping("/{id}/participants")
    public List<EventParticipant> addParticipant(@PathVariable UUID id, @Valid @RequestBody EventDtos.ParticipantRequest request) {
        return eventService.addParticipant(id, request);
    }

    @DeleteMapping("/{id}/participants/{participantId}")
    public void removeParticipant(@PathVariable UUID id, @PathVariable UUID participantId) {
        eventService.removeParticipant(id, participantId);
    }

    @GetMapping("/columns")
    public List<ColumnDefinition> getColumns(@RequestParam UUID tenantId) {
        return definitionService.getColumns(tenantId, DomainEnums.DefinitionModule.EVENT);
    }

    @PatchMapping("/columns")
    public List<ColumnDefinition> upsertColumns(@Valid @RequestBody List<ProgramDtos.ColumnRequest> requests) {
        return definitionService.upsertColumns(DomainEnums.DefinitionModule.EVENT, requests);
    }

    @PostMapping("/columns")
    public ColumnDefinition createColumn(@Valid @RequestBody ProgramDtos.ColumnRequest request) {
        return definitionService.createColumn(DomainEnums.DefinitionModule.EVENT, request);
    }

    @DeleteMapping("/columns/{id}")
    public void deleteColumn(@PathVariable UUID id) {
        definitionService.deleteColumn(id);
    }
}
