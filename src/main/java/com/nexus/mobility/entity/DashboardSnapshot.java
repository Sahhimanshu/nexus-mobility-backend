package com.nexus.mobility.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "dashboard_snapshots")
public class DashboardSnapshot extends BaseEntity {

    @Column(nullable = false)
    private UUID tenantId;

    @Column(nullable = false)
    private LocalDate snapshotDate;

    private Integer totalPrograms;
    private Integer totalPartnerships;
    private Integer totalEvents;
    private Integer totalVisits;
    private Integer totalStudents;

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public LocalDate getSnapshotDate() {
        return snapshotDate;
    }

    public void setSnapshotDate(LocalDate snapshotDate) {
        this.snapshotDate = snapshotDate;
    }

    public Integer getTotalPrograms() {
        return totalPrograms;
    }

    public void setTotalPrograms(Integer totalPrograms) {
        this.totalPrograms = totalPrograms;
    }

    public Integer getTotalPartnerships() {
        return totalPartnerships;
    }

    public void setTotalPartnerships(Integer totalPartnerships) {
        this.totalPartnerships = totalPartnerships;
    }

    public Integer getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(Integer totalEvents) {
        this.totalEvents = totalEvents;
    }

    public Integer getTotalVisits() {
        return totalVisits;
    }

    public void setTotalVisits(Integer totalVisits) {
        this.totalVisits = totalVisits;
    }

    public Integer getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(Integer totalStudents) {
        this.totalStudents = totalStudents;
    }
}
