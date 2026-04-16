package com.nexus.mobility.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "programs")
public class Program extends BaseEntity {

    @Column(nullable = false)
    private UUID tenantId;

    private UUID partnershipId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DomainEnums.ProgramType type;

    private String partnerUniversity;
    private String countryCode;
    private Integer seats;
    private Integer enrolled;
    private LocalDate deadline;
    private String durationLabel;
    private Boolean scholarshipAvailable;
    @Column(length = 4000)
    private String description;

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public UUID getPartnershipId() {
        return partnershipId;
    }

    public void setPartnershipId(UUID partnershipId) {
        this.partnershipId = partnershipId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DomainEnums.ProgramType getType() {
        return type;
    }

    public void setType(DomainEnums.ProgramType type) {
        this.type = type;
    }

    public String getPartnerUniversity() {
        return partnerUniversity;
    }

    public void setPartnerUniversity(String partnerUniversity) {
        this.partnerUniversity = partnerUniversity;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Integer getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(Integer enrolled) {
        this.enrolled = enrolled;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getDurationLabel() {
        return durationLabel;
    }

    public void setDurationLabel(String durationLabel) {
        this.durationLabel = durationLabel;
    }

    public Boolean getScholarshipAvailable() {
        return scholarshipAvailable;
    }

    public void setScholarshipAvailable(Boolean scholarshipAvailable) {
        this.scholarshipAvailable = scholarshipAvailable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
