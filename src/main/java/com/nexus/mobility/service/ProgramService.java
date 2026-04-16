package com.nexus.mobility.service;

import com.nexus.mobility.dto.ApiDtos;
import com.nexus.mobility.dto.ProgramDtos;
import com.nexus.mobility.entity.Program;
import com.nexus.mobility.entity.ProgramStudent;
import com.nexus.mobility.entity.Student;
import com.nexus.mobility.exception.BadRequestException;
import com.nexus.mobility.exception.ResourceNotFoundException;
import com.nexus.mobility.repository.ProgramRepository;
import com.nexus.mobility.repository.ProgramStudentRepository;
import com.nexus.mobility.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class ProgramService {

    private final ProgramRepository programRepository;
    private final StudentRepository studentRepository;
    private final ProgramStudentRepository programStudentRepository;

    public ProgramService(ProgramRepository programRepository, StudentRepository studentRepository, ProgramStudentRepository programStudentRepository) {
        this.programRepository = programRepository;
        this.studentRepository = studentRepository;
        this.programStudentRepository = programStudentRepository;
    }

    public ApiDtos.PageResponse<Program> list(UUID tenantId, String type, String search, Integer page, Integer limit) {
        List<Program> items = programRepository.findByTenantId(tenantId).stream()
                .filter(program -> type == null || program.getType().name().equalsIgnoreCase(type))
                .filter(program -> search == null || program.getName().toLowerCase().contains(search.toLowerCase())
                        || (program.getPartnerUniversity() != null && program.getPartnerUniversity().toLowerCase().contains(search.toLowerCase())))
                .sorted(Comparator.comparing(Program::getCreatedAt).reversed())
                .toList();
        return PageMapper.page(items, page, limit);
    }

    public Program get(UUID id) {
        return programRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found."));
    }

    @Transactional
    public Program create(ProgramDtos.ProgramRequest request) {
        Program program = new Program();
        apply(program, request);
        return programRepository.save(program);
    }

    @Transactional
    public Program update(UUID id, ProgramDtos.ProgramRequest request) {
        Program program = get(id);
        apply(program, request);
        return programRepository.save(program);
    }

    @Transactional
    public void delete(UUID id) {
        Program program = get(id);
        programRepository.delete(program);
    }

    public List<Student> students(UUID programId) {
        get(programId);
        List<UUID> studentIds = programStudentRepository.findByProgramId(programId).stream().map(ProgramStudent::getStudentId).toList();
        return studentRepository.findAllById(studentIds);
    }

    @Transactional
    public List<Student> addStudent(UUID programId, ProgramDtos.ProgramStudentRequest request) {
        Program program = get(programId);
        Student student = studentRepository.findById(request.studentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found."));
        if (!student.getTenantId().equals(program.getTenantId())) {
            throw new BadRequestException("Student does not belong to the same tenant.");
        }
        boolean alreadyLinked = programStudentRepository.findByProgramIdAndStudentId(programId, student.getId()).isPresent();
        if (!alreadyLinked) {
            ProgramStudent programStudent = new ProgramStudent();
            programStudent.setProgramId(programId);
            programStudent.setStudentId(student.getId());
            programStudent.setTenantId(program.getTenantId());
            programStudentRepository.save(programStudent);
        }
        return students(programId);
    }

    @Transactional
    public void removeStudent(UUID programId, UUID studentId) {
        ProgramStudent mapping = programStudentRepository.findByProgramIdAndStudentId(programId, studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Program student mapping not found."));
        programStudentRepository.delete(mapping);
    }

    private void apply(Program program, ProgramDtos.ProgramRequest request) {
        program.setTenantId(request.tenantId());
        program.setPartnershipId(request.partnershipId());
        program.setName(request.name());
        program.setType(request.type());
        program.setPartnerUniversity(request.partnerUniversity());
        program.setCountryCode(request.countryCode());
        program.setSeats(request.seats());
        program.setEnrolled(request.enrolled());
        program.setDeadline(request.deadline());
        program.setDurationLabel(request.durationLabel());
        program.setScholarshipAvailable(request.scholarshipAvailable());
        program.setDescription(request.description());
    }
}
