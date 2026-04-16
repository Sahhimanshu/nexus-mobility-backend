package com.nexus.mobility.service;

import com.nexus.mobility.dto.ApiDtos;
import com.nexus.mobility.dto.StudentDtos;
import com.nexus.mobility.entity.Student;
import com.nexus.mobility.exception.ResourceNotFoundException;
import com.nexus.mobility.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public ApiDtos.PageResponse<Student> list(UUID tenantId, String search, String status, Integer page, Integer limit) {
        List<Student> items = studentRepository.findByTenantId(tenantId).stream()
                .filter(student -> status == null || status.isBlank() || status.equalsIgnoreCase(student.getStatus()))
                .filter(student -> {
                    if (search == null || search.isBlank()) {
                        return true;
                    }
                    String query = search.toLowerCase();
                    return contains(student.getFullName(), query)
                            || contains(student.getEmail(), query)
                            || contains(student.getHostUniversity(), query)
                            || contains(student.getProgramName(), query);
                })
                .sorted(Comparator.comparing(Student::getCreatedAt).reversed())
                .toList();
        return PageMapper.page(items, page, limit);
    }

    public Student get(UUID id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found."));
    }

    @Transactional
    public Student create(StudentDtos.StudentRequest request) {
        Student student = new Student();
        apply(student, request);
        return studentRepository.save(student);
    }

    @Transactional
    public Student update(UUID id, StudentDtos.StudentRequest request) {
        Student student = get(id);
        apply(student, request);
        return studentRepository.save(student);
    }

    @Transactional
    public void delete(UUID id) {
        studentRepository.delete(get(id));
    }

    private void apply(Student student, StudentDtos.StudentRequest request) {
        student.setTenantId(request.tenantId());
        student.setFullName(request.fullName());
        student.setEmail(request.email().toLowerCase());
        student.setHomeUniversity(request.homeUniversity());
        student.setHostUniversity(request.hostUniversity());
        student.setHostCountryCode(request.hostCountryCode());
        student.setProgramName(request.programName());
        student.setSemesterLabel(request.semesterLabel());
        student.setGpa(request.gpa());
        student.setStatus(request.status());
    }

    private boolean contains(String source, String query) {
        return source != null && source.toLowerCase().contains(query);
    }
}
