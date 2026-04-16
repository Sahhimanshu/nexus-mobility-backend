package com.nexus.mobility.controller;

import com.nexus.mobility.entity.DashboardSnapshot;
import com.nexus.mobility.service.DashboardService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping({"/api/dashboard", "/api/v1/dashboard"})
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping({"/snapshot", "/overview"})
    public Map<String, Object> snapshot(@RequestParam UUID tenantId,
                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return dashboardService.snapshotPayload(tenantId, date == null ? LocalDate.now() : date);
    }

    @GetMapping("/mobility-trend")
    public List<Map<String, Object>> trend(@RequestParam UUID tenantId,
                                           @RequestParam(defaultValue = "5") Integer years) {
        return dashboardService.mobilityTrend(tenantId, years);
    }

    @GetMapping("/country-stats")
    public Object countryStats(@RequestParam UUID tenantId, @RequestParam(required = false) Integer year) {
        return dashboardService.countryStats(tenantId, year);
    }

    @PostMapping("/snapshot/refresh")
    public DashboardSnapshot refresh(@RequestParam UUID tenantId,
                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return dashboardService.refreshSnapshot(tenantId, date == null ? LocalDate.now() : date);
    }
}
