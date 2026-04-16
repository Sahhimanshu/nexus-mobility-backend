package com.nexus.mobility.controller;

import com.nexus.mobility.entity.DocumentRecord;
import com.nexus.mobility.entity.DomainEnums;
import com.nexus.mobility.service.DocumentService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping({"/api/documents", "/api/v1/documents"})
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    public List<DocumentRecord> list(@RequestParam UUID tenantId,
                                     @RequestParam(required = false) UUID programId,
                                     @RequestParam(required = false) UUID partnershipId,
                                     @RequestParam(required = false) String type) {
        return documentService.list(tenantId, programId, partnershipId, type);
    }

    @PostMapping(value = {"", "/upload"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DocumentRecord upload(@RequestParam UUID tenantId,
                                 @RequestParam(required = false) UUID programId,
                                 @RequestParam(required = false) UUID partnershipId,
                                 @RequestParam DomainEnums.DocumentType type,
                                 @RequestPart("file") MultipartFile file) throws IOException {
        return documentService.upload(tenantId, programId, partnershipId, type, file);
    }

    @GetMapping("/{id}")
    public DocumentRecord get(@PathVariable UUID id) {
        return documentService.get(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) throws IOException {
        documentService.delete(id);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable UUID id) {
        DocumentRecord document = documentService.get(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getOriginalFilename() + "\"")
                .contentType(MediaType.parseMediaType(document.getContentType() == null ? MediaType.APPLICATION_OCTET_STREAM_VALUE : document.getContentType()))
                .body(documentService.download(id));
    }
}
