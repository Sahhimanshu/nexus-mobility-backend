package com.nexus.mobility.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "partnerships")
public class Partnership extends BaseEntity {

    @Column(nullable = false)
    private UUID tenantId;

    @Column(nullable = false)
    private String universityName;

    @Column(nullable = false)
    private String countryCode;

    private String partnershipType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DomainEnums.PartnershipStatus status = DomainEnums.PartnershipStatus.PENDING;

    private LocalDate startDate;
    private LocalDate expiryDate;
    private boolean mouSigned;
    private Integer renewalAlertDays;
    @Column(length = 4000)
    private String notes;

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPartnershipType() {
        return partnershipType;
    }

    public void setPartnershipType(String partnershipType) {
        this.partnershipType = partnershipType;
    }

    public DomainEnums.PartnershipStatus getStatus() {
        return status;
    }

    public void setStatus(DomainEnums.PartnershipStatus status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isMouSigned() {
        return mouSigned;
    }

    public void setMouSigned(boolean mouSigned) {
        this.mouSigned = mouSigned;
    }

    public Integer getRenewalAlertDays() {
        return renewalAlertDays;
    }

    public void setRenewalAlertDays(Integer renewalAlertDays) {
        this.renewalAlertDays = renewalAlertDays;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
