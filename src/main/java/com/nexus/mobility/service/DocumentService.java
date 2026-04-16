package com.nexus.mobility.service;

import com.nexus.mobility.config.StorageProperties;
import com.nexus.mobility.entity.DocumentRecord;
import com.nexus.mobility.entity.DomainEnums;
import com.nexus.mobility.exception.ResourceNotFoundException;
import com.nexus.mobility.repository.DocumentRecordRepository;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentService {

    private final DocumentRecordRepository documentRecordRepository;
    private final Path storageRoot;

    public DocumentService(DocumentRecordRepository documentRecordRepository, StorageProperties storageProperties) throws IOException {
        this.documentRecordRepository = documentRecordRepository;
        this.storageRoot = Path.of(storageProperties.path()).toAbsolutePath().normalize();
        Files.createDirectories(this.storageRoot);
    }

    public List<DocumentRecord> list(UUID tenantId, UUID programId, UUID partnershipId, String type) {
        return documentRecordRepository.findByTenantId(tenantId).stream()
                .filter(document -> programId == null || programId.equals(document.getProgramId()))
                .filter(document -> partnershipId == null || partnershipId.equals(document.getPartnershipId()))
                .filter(document -> type == null || document.getType().name().equalsIgnoreCase(type))
                .sorted(Comparator.comparing(DocumentRecord::getCreatedAt).reversed())
                .toList();
    }

    public DocumentRecord get(UUID id) {
        return documentRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found."));
    }

    @Transactional
    public DocumentRecord upload(UUID tenantId, UUID programId, UUID partnershipId, DomainEnums.DocumentType type, MultipartFile file) throws IOException {
        String storedName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path target = storageRoot.resolve(storedName);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        DocumentRecord document = new DocumentRecord();
        document.setTenantId(tenantId);
        document.setProgramId(programId);
        document.setPartnershipId(partnershipId);
        document.setOriginalFilename(file.getOriginalFilename());
        document.setStoredFilename(storedName);
        document.setContentType(file.getContentType());
        document.setSizeBytes(file.getSize());
        document.setType(type);
        document.setStoragePath(target.toString());
        return documentRecordRepository.save(document);
    }

    @Transactional
    public void delete(UUID id) throws IOException {
        DocumentRecord document = get(id);
        Files.deleteIfExists(Path.of(document.getStoragePath()));
        documentRecordRepository.delete(document);
    }

    public Resource download(UUID id) {
        DocumentRecord document = get(id);
        return new FileSystemResource(document.getStoragePath());
    }
}
