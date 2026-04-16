package com.nexus.mobility.service;

import com.nexus.mobility.entity.CountryStat;
import com.nexus.mobility.entity.DashboardSnapshot;
import com.nexus.mobility.repository.CountryStatRepository;
import com.nexus.mobility.repository.DashboardSnapshotRepository;
import com.nexus.mobility.repository.EventRepository;
import com.nexus.mobility.repository.PartnershipRepository;
import com.nexus.mobility.repository.ProgramRepository;
import com.nexus.mobility.repository.StudentRepository;
import com.nexus.mobility.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Year;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DashboardService {

    private final DashboardSnapshotRepository dashboardSnapshotRepository;
    private final ProgramRepository programRepository;
    private final PartnershipRepository partnershipRepository;
    private final EventRepository eventRepository;
    private final VisitRepository visitRepository;
    private final StudentRepository studentRepository;
    private final CountryStatRepository countryStatRepository;

    public DashboardService(
            DashboardSnapshotRepository dashboardSnapshotRepository,
            ProgramRepository programRepository,
            PartnershipRepository partnershipRepository,
            EventRepository eventRepository,
            VisitRepository visitRepository,
            StudentRepository studentRepository,
            CountryStatRepository countryStatRepository
    ) {
        this.dashboardSnapshotRepository = dashboardSnapshotRepository;
        this.programRepository = programRepository;
        this.partnershipRepository = partnershipRepository;
        this.eventRepository = eventRepository;
        this.visitRepository = visitRepository;
        this.studentRepository = studentRepository;
        this.countryStatRepository = countryStatRepository;
    }

    public DashboardSnapshot snapshot(UUID tenantId, LocalDate date) {
        return dashboardSnapshotRepository.findByTenantIdAndSnapshotDate(tenantId, date)
                .orElseGet(() -> refreshSnapshot(tenantId, date));
    }

    public List<Map<String, Object>> mobilityTrend(UUID tenantId, int years) {
        int currentYear = Year.now().getValue();
        return java.util.stream.IntStream.range(0, years)
                .mapToObj(index -> currentYear - (years - index - 1))
                .map(year -> {
                    List<CountryStat> stats = countryStatRepository.findByTenantIdAndSnapshotYear(tenantId, year);
                    int total = stats.stream().mapToInt(stat -> stat.getOutboundStudents() == null ? 0 : stat.getOutboundStudents()).sum();
                    return Map.<String, Object>of("year", year, "students", total);
                })
                .toList();
    }

    public List<CountryStat> countryStats(UUID tenantId, Integer year) {
        return year == null ? countryStatRepository.findByTenantId(tenantId) : countryStatRepository.findByTenantIdAndSnapshotYear(tenantId, year);
    }

    @Transactional
    public DashboardSnapshot refreshSnapshot(UUID tenantId, LocalDate date) {
        DashboardSnapshot snapshot = dashboardSnapshotRepository.findByTenantIdAndSnapshotDate(tenantId, date)
                .orElseGet(DashboardSnapshot::new);
        snapshot.setTenantId(tenantId);
        snapshot.setSnapshotDate(date);
        snapshot.setTotalPrograms(programRepository.findByTenantId(tenantId).size());
        snapshot.setTotalPartnerships(partnershipRepository.findByTenantId(tenantId).size());
        snapshot.setTotalEvents(eventRepository.findByTenantId(tenantId).size());
        snapshot.setTotalVisits(visitRepository.findByTenantId(tenantId).size());
        snapshot.setTotalStudents(studentRepository.findByTenantId(tenantId).size());
        return dashboardSnapshotRepository.save(snapshot);
    }

    public Map<String, Object> snapshotPayload(UUID tenantId, LocalDate date) {
        DashboardSnapshot snapshot = snapshot(tenantId, date);
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("date", snapshot.getSnapshotDate());
        payload.put("totalPrograms", snapshot.getTotalPrograms());
        payload.put("totalPartnerships", snapshot.getTotalPartnerships());
        payload.put("totalEvents", snapshot.getTotalEvents());
        payload.put("totalVisits", snapshot.getTotalVisits());
        payload.put("totalStudents", snapshot.getTotalStudents());
        return payload;
    }
}
