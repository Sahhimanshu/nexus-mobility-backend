package com.nexus.mobility.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "country_stats")
public class CountryStat extends BaseEntity {

    @Column(nullable = false)
    private UUID tenantId;

    @Column(nullable = false, length = 3)
    private String countryCode;

    private Integer snapshotYear;
    private Integer outboundStudents;
    private Integer inboundStudents;
    private Integer partnershipCount;

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getSnapshotYear() {
        return snapshotYear;
    }

    public void setSnapshotYear(Integer snapshotYear) {
        this.snapshotYear = snapshotYear;
    }

    public Integer getOutboundStudents() {
        return outboundStudents;
    }

    public void setOutboundStudents(Integer outboundStudents) {
        this.outboundStudents = outboundStudents;
    }

    public Integer getInboundStudents() {
        return inboundStudents;
    }

    public void setInboundStudents(Integer inboundStudents) {
        this.inboundStudents = inboundStudents;
    }

    public Integer getPartnershipCount() {
        return partnershipCount;
    }

    public void setPartnershipCount(Integer partnershipCount) {
        this.partnershipCount = partnershipCount;
    }
}
