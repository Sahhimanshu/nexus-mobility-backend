package com.nexus.mobility.service;

import com.nexus.mobility.dto.UserTenantDtos;
import com.nexus.mobility.entity.TenantSettings;
import com.nexus.mobility.repository.TenantSettingsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TenantService {

    private final TenantSettingsRepository tenantSettingsRepository;

    public TenantService(TenantSettingsRepository tenantSettingsRepository) {
        this.tenantSettingsRepository = tenantSettingsRepository;
    }

    public TenantSettings getSettings(UUID tenantId) {
        return tenantSettingsRepository.findByTenantId(tenantId).orElseGet(() -> {
            TenantSettings settings = new TenantSettings();
            settings.setTenantId(tenantId);
            return tenantSettingsRepository.save(settings);
        });
    }

    @Transactional
    public TenantSettings updateSettings(UserTenantDtos.TenantSettingsRequest request) {
        TenantSettings settings = getSettings(request.tenantId());
        if (request.timezone() != null) {
            settings.setTimezone(request.timezone());
        }
        if (request.currency() != null) {
            settings.setCurrency(request.currency());
        }
        if (request.locale() != null) {
            settings.setLocale(request.locale());
        }
        if (request.contactEmail() != null) {
            settings.setContactEmail(request.contactEmail());
        }
        if (request.brandingColor() != null) {
            settings.setBrandingColor(request.brandingColor());
        }
        return tenantSettingsRepository.save(settings);
    }
}
