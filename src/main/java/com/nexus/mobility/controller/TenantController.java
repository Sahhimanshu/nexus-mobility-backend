package com.nexus.mobility.controller;

import com.nexus.mobility.dto.UserTenantDtos;
import com.nexus.mobility.entity.TenantSettings;
import com.nexus.mobility.service.TenantService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping({"/api/tenant", "/api/v1/tenant"})
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping("/settings")
    public TenantSettings settings(@RequestParam UUID tenantId) {
        return tenantService.getSettings(tenantId);
    }

    @PatchMapping("/settings")
    public TenantSettings updateSettings(@Valid @RequestBody UserTenantDtos.TenantSettingsRequest request) {
        return tenantService.updateSettings(request);
    }
}
