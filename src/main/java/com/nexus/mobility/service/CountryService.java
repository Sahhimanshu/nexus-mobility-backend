package com.nexus.mobility.service;

import com.nexus.mobility.dto.CountryDtos;
import com.nexus.mobility.entity.Country;
import com.nexus.mobility.entity.CountryStat;
import com.nexus.mobility.exception.ResourceNotFoundException;
import com.nexus.mobility.repository.CountryRepository;
import com.nexus.mobility.repository.CountryStatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CountryService {

    private final CountryRepository countryRepository;
    private final CountryStatRepository countryStatRepository;

    public CountryService(CountryRepository countryRepository, CountryStatRepository countryStatRepository) {
        this.countryRepository = countryRepository;
        this.countryStatRepository = countryStatRepository;
    }

    public List<Country> countries() {
        return countryRepository.findAll();
    }

    public Country getCountry(String code) {
        return countryRepository.findById(code.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Country not found."));
    }

    public List<CountryStat> stats(UUID tenantId) {
        return countryStatRepository.findByTenantId(tenantId);
    }

    @Transactional
    public CountryStat patchStat(UUID tenantId, String countryCode, CountryDtos.CountryStatRequest request) {
        CountryStat stat = countryStatRepository.findByTenantIdAndCountryCode(tenantId, countryCode.toUpperCase())
                .orElseGet(CountryStat::new);
        stat.setTenantId(tenantId);
        stat.setCountryCode(countryCode.toUpperCase());
        if (request.snapshotYear() != null) {
            stat.setSnapshotYear(request.snapshotYear());
        }
        if (request.outboundStudents() != null) {
            stat.setOutboundStudents(request.outboundStudents());
        }
        if (request.inboundStudents() != null) {
            stat.setInboundStudents(request.inboundStudents());
        }
        if (request.partnershipCount() != null) {
            stat.setPartnershipCount(request.partnershipCount());
        }
        return countryStatRepository.save(stat);
    }
}
