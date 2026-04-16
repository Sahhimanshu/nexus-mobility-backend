package com.nexus.mobility.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "visits")
public class Visit extends BaseEntity {

    @Column(nullable = false)
    private UUID tenantId;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DomainEnums.VisitType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DomainEnums.VisitStatus status = DomainEnums.VisitStatus.PLANNED;

    private LocalDate visitDate;
    private String location;
    private String institutionName;
    @Column(length = 4000)
    private String agenda;

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DomainEnums.VisitType getType() {
        return type;
    }

    public void setType(DomainEnums.VisitType type) {
        this.type = type;
    }

    public DomainEnums.VisitStatus getStatus() {
        return status;
    }

    public void setStatus(DomainEnums.VisitStatus status) {
        this.status = status;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }
}
