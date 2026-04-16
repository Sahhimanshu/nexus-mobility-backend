package com.nexus.mobility.controller;

import com.nexus.mobility.dto.PartnershipDtos;
import com.nexus.mobility.dto.ProgramDtos;
import com.nexus.mobility.entity.ColumnDefinition;
import com.nexus.mobility.entity.DomainEnums;
import com.nexus.mobility.entity.Partnership;
import com.nexus.mobility.service.DefinitionService;
import com.nexus.mobility.service.PartnershipService;
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
@RequestMapping({"/api/partnerships", "/api/v1/partnerships"})
public class PartnershipController {

    private final PartnershipService partnershipService;
    private final DefinitionService definitionService;

    public PartnershipController(PartnershipService partnershipService, DefinitionService definitionService) {
        this.partnershipService = partnershipService;
        this.definitionService = definitionService;
    }

    @GetMapping
    public Object list(@RequestParam UUID tenantId,
                       @RequestParam(required = false) String status,
                       @RequestParam(required = false) String search,
                       @RequestParam(required = false) Integer page,
                       @RequestParam(required = false) Integer limit) {
        return partnershipService.list(tenantId, status, search, page, limit);
    }

    @PostMapping
    public Partnership create(@Valid @RequestBody PartnershipDtos.PartnershipRequest request) {
        return partnershipService.create(request);
    }

    @GetMapping("/{id}")
    public Partnership get(@PathVariable UUID id) {
        return partnershipService.get(id);
    }

    @PatchMapping("/{id}")
    @PutMapping("/{id}")
    public Partnership update(@PathVariable UUID id, @Valid @RequestBody PartnershipDtos.PartnershipRequest request) {
        return partnershipService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        partnershipService.delete(id);
    }

    @GetMapping("/columns")
    public List<ColumnDefinition> getColumns(@RequestParam UUID tenantId) {
        return definitionService.getColumns(tenantId, DomainEnums.DefinitionModule.PARTNERSHIP);
    }

    @PatchMapping("/columns")
    public List<ColumnDefinition> upsertColumns(@Valid @RequestBody List<ProgramDtos.ColumnRequest> requests) {
        return definitionService.upsertColumns(DomainEnums.DefinitionModule.PARTNERSHIP, requests);
    }

    @PostMapping("/columns")
    public ColumnDefinition createColumn(@Valid @RequestBody ProgramDtos.ColumnRequest request) {
        return definitionService.createColumn(DomainEnums.DefinitionModule.PARTNERSHIP, request);
    }

    @DeleteMapping("/columns/{id}")
    public void deleteColumn(@PathVariable UUID id) {
        definitionService.deleteColumn(id);
    }
}
