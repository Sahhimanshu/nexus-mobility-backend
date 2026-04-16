package com.nexus.mobility.controller;

import com.nexus.mobility.dto.StudentDtos;
import com.nexus.mobility.entity.Student;
import com.nexus.mobility.service.StudentService;
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

import java.util.UUID;

@RestController
@RequestMapping({"/api/students", "/api/v1/students"})
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public Object list(@RequestParam UUID tenantId,
                       @RequestParam(required = false) String search,
                       @RequestParam(required = false) String status,
                       @RequestParam(required = false) Integer page,
                       @RequestParam(required = false) Integer limit) {
        return studentService.list(tenantId, search, status, page, limit);
    }

    @PostMapping
    public Student create(@Valid @RequestBody StudentDtos.StudentRequest request) {
        return studentService.create(request);
    }

    @GetMapping("/{id}")
    public Student get(@PathVariable UUID id) {
        return studentService.get(id);
    }

    @PatchMapping("/{id}")
    @PutMapping("/{id}")
    public Student update(@PathVariable UUID id, @Valid @RequestBody StudentDtos.StudentRequest request) {
        return studentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        studentService.delete(id);
    }
}
