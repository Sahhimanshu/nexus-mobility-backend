package com.nexus.mobility.controller;

import com.nexus.mobility.dto.ProgramDtos;
import com.nexus.mobility.entity.ColumnDefinition;
import com.nexus.mobility.entity.DomainEnums;
import com.nexus.mobility.entity.FieldDefinition;
import com.nexus.mobility.entity.Program;
import com.nexus.mobility.entity.Student;
import com.nexus.mobility.service.DefinitionService;
import com.nexus.mobility.service.ProgramService;
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
@RequestMapping({"/api/programs", "/api/v1/programs"})
public class ProgramController {

    private final ProgramService programService;
    private final DefinitionService definitionService;

    public ProgramController(ProgramService programService, DefinitionService definitionService) {
        this.programService = programService;
        this.definitionService = definitionService;
    }

    @GetMapping
    public Object list(@RequestParam UUID tenantId,
                       @RequestParam(required = false) String type,
                       @RequestParam(required = false) String search,
                       @RequestParam(required = false) Integer page,
                       @RequestParam(required = false) Integer limit) {
        return programService.list(tenantId, type, search, page, limit);
    }

    @PostMapping
    public Program create(@Valid @RequestBody ProgramDtos.ProgramRequest request) {
        return programService.create(request);
    }

    @GetMapping("/{id}")
    public Program get(@PathVariable UUID id) {
        return programService.get(id);
    }

    @PatchMapping("/{id}")
    @PutMapping("/{id}")
    public Program update(@PathVariable UUID id, @Valid @RequestBody ProgramDtos.ProgramRequest request) {
        return programService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        programService.delete(id);
    }

    @GetMapping("/{id}/students")
    public List<Student> students(@PathVariable UUID id) {
        return programService.students(id);
    }

    @PostMapping("/{id}/students")
    public List<Student> addStudent(@PathVariable UUID id, @Valid @RequestBody ProgramDtos.ProgramStudentRequest request) {
        return programService.addStudent(id, request);
    }

    @DeleteMapping("/{id}/students/{studentId}")
    public void removeStudent(@PathVariable UUID id, @PathVariable UUID studentId) {
        programService.removeStudent(id, studentId);
    }

    @GetMapping("/columns")
    public List<ColumnDefinition> getColumns(@RequestParam UUID tenantId) {
        return definitionService.getColumns(tenantId, DomainEnums.DefinitionModule.PROGRAM);
    }

    @PatchMapping("/columns")
    public List<ColumnDefinition> upsertColumns(@Valid @RequestBody List<ProgramDtos.ColumnRequest> requests) {
        return definitionService.upsertColumns(DomainEnums.DefinitionModule.PROGRAM, requests);
    }

    @PostMapping("/columns")
    public ColumnDefinition createColumn(@Valid @RequestBody ProgramDtos.ColumnRequest request) {
        return definitionService.createColumn(DomainEnums.DefinitionModule.PROGRAM, request);
    }

    @DeleteMapping("/columns/{id}")
    public void deleteColumn(@PathVariable UUID id) {
        definitionService.deleteColumn(id);
    }

    @GetMapping("/fields")
    public List<FieldDefinition> getFields(@RequestParam UUID tenantId) {
        return definitionService.getFields(tenantId);
    }

    @PatchMapping("/fields")
    public List<FieldDefinition> upsertFields(@Valid @RequestBody List<ProgramDtos.FieldRequest> requests) {
        return definitionService.upsertFields(requests);
    }

    @PostMapping("/fields")
    public FieldDefinition createField(@Valid @RequestBody ProgramDtos.FieldRequest request) {
        return definitionService.createField(request);
    }

    @DeleteMapping("/fields/{id}")
    public void deleteField(@PathVariable UUID id) {
        definitionService.deleteField(id);
    }
}
