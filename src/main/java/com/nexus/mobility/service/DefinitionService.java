package com.nexus.mobility.service;

import com.nexus.mobility.dto.ProgramDtos;
import com.nexus.mobility.entity.ColumnDefinition;
import com.nexus.mobility.entity.DomainEnums;
import com.nexus.mobility.entity.FieldDefinition;
import com.nexus.mobility.exception.ResourceNotFoundException;
import com.nexus.mobility.repository.ColumnDefinitionRepository;
import com.nexus.mobility.repository.FieldDefinitionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DefinitionService {

    private final ColumnDefinitionRepository columnDefinitionRepository;
    private final FieldDefinitionRepository fieldDefinitionRepository;

    public DefinitionService(ColumnDefinitionRepository columnDefinitionRepository, FieldDefinitionRepository fieldDefinitionRepository) {
        this.columnDefinitionRepository = columnDefinitionRepository;
        this.fieldDefinitionRepository = fieldDefinitionRepository;
    }

    public List<ColumnDefinition> getColumns(UUID tenantId, DomainEnums.DefinitionModule module) {
        return columnDefinitionRepository.findByTenantIdAndModuleOrderBySortOrderAsc(tenantId, module);
    }

    @Transactional
    public List<ColumnDefinition> upsertColumns(DomainEnums.DefinitionModule module, List<ProgramDtos.ColumnRequest> requests) {
        requests.forEach(request -> {
            ColumnDefinition definition = request.id() == null ? new ColumnDefinition() : columnDefinitionRepository.findById(request.id())
                    .orElse(new ColumnDefinition());
            definition.setTenantId(request.tenantId());
            definition.setModule(module);
            definition.setKeyName(request.keyName());
            definition.setLabel(request.label());
            definition.setValueType(request.valueType());
            definition.setSortOrder(request.sortOrder());
            definition.setVisible(request.visible() == null || request.visible());
            columnDefinitionRepository.save(definition);
        });
        return getColumns(requests.getFirst().tenantId(), module);
    }

    @Transactional
    public ColumnDefinition createColumn(DomainEnums.DefinitionModule module, ProgramDtos.ColumnRequest request) {
        ColumnDefinition definition = new ColumnDefinition();
        definition.setTenantId(request.tenantId());
        definition.setModule(module);
        definition.setKeyName(request.keyName());
        definition.setLabel(request.label());
        definition.setValueType(request.valueType());
        definition.setSortOrder(request.sortOrder());
        definition.setVisible(request.visible() == null || request.visible());
        return columnDefinitionRepository.save(definition);
    }

    @Transactional
    public void deleteColumn(UUID id) {
        columnDefinitionRepository.delete(columnDefinitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Column definition not found.")));
    }

    public List<FieldDefinition> getFields(UUID tenantId) {
        return fieldDefinitionRepository.findByTenantIdOrderBySortOrderAsc(tenantId);
    }

    @Transactional
    public List<FieldDefinition> upsertFields(List<ProgramDtos.FieldRequest> requests) {
        requests.forEach(request -> {
            FieldDefinition definition = request.id() == null ? new FieldDefinition() : fieldDefinitionRepository.findById(request.id())
                    .orElse(new FieldDefinition());
            definition.setTenantId(request.tenantId());
            definition.setKeyName(request.keyName());
            definition.setLabel(request.label());
            definition.setInputType(request.inputType());
            definition.setRequiredField(Boolean.TRUE.equals(request.requiredField()));
            definition.setSortOrder(request.sortOrder());
            fieldDefinitionRepository.save(definition);
        });
        return getFields(requests.getFirst().tenantId());
    }

    @Transactional
    public FieldDefinition createField(ProgramDtos.FieldRequest request) {
        FieldDefinition definition = new FieldDefinition();
        definition.setTenantId(request.tenantId());
        definition.setKeyName(request.keyName());
        definition.setLabel(request.label());
        definition.setInputType(request.inputType());
        definition.setRequiredField(Boolean.TRUE.equals(request.requiredField()));
        definition.setSortOrder(request.sortOrder());
        return fieldDefinitionRepository.save(definition);
    }

    @Transactional
    public void deleteField(UUID id) {
        fieldDefinitionRepository.delete(fieldDefinitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Field definition not found.")));
    }
}
