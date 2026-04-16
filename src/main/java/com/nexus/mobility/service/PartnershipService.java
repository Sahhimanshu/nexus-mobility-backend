package com.nexus.mobility.service;

import com.nexus.mobility.dto.ApiDtos;
import com.nexus.mobility.dto.PartnershipDtos;
import com.nexus.mobility.entity.Partnership;
import com.nexus.mobility.exception.ResourceNotFoundException;
import com.nexus.mobility.repository.PartnershipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class PartnershipService {

    private final PartnershipRepository partnershipRepository;

    public PartnershipService(PartnershipRepository partnershipRepository) {
        this.partnershipRepository = partnershipRepository;
    }

    public ApiDtos.PageResponse<Partnership> list(UUID tenantId, String status, String search, Integer page, Integer limit) {
        List<Partnership> items = partnershipRepository.findByTenantId(tenantId).stream()
                .filter(item -> status == null || item.getStatus().name().equalsIgnoreCase(status))
                .filter(item -> search == null || item.getUniversityName().toLowerCase().contains(search.toLowerCase())
                        || item.getCountryCode().toLowerCase().contains(search.toLowerCase()))
                .sorted(Comparator.comparing(Partnership::getCreatedAt).reversed())
                .toList();
        return PageMapper.page(items, page, limit);
    }

    public Partnership get(UUID id) {
        return partnershipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partnership not found."));
    }

    @Transactional
    public Partnership create(PartnershipDtos.PartnershipRequest request) {
        Partnership partnership = new Partnership();
        apply(partnership, request);
        return partnershipRepository.save(partnership);
    }

    @Transactional
    public Partnership update(UUID id, PartnershipDtos.PartnershipRequest request) {
        Partnership partnership = get(id);
        apply(partnership, request);
        return partnershipRepository.save(partnership);
    }

    @Transactional
    public void delete(UUID id) {
        partnershipRepository.delete(get(id));
    }

    private void apply(Partnership partnership, PartnershipDtos.PartnershipRequest request) {
        partnership.setTenantId(request.tenantId());
        partnership.setUniversityName(request.universityName());
        partnership.setCountryCode(request.countryCode());
        partnership.setPartnershipType(request.partnershipType());
        if (request.status() != null) {
            partnership.setStatus(request.status());
        }
        partnership.setStartDate(request.startDate());
        partnership.setExpiryDate(request.expiryDate());
        partnership.setMouSigned(Boolean.TRUE.equals(request.mouSigned()));
        partnership.setRenewalAlertDays(request.renewalAlertDays());
        partnership.setNotes(request.notes());
    }
}
