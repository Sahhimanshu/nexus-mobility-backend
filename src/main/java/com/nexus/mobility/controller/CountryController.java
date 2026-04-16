package com.nexus.mobility.controller;

import com.nexus.mobility.dto.CountryDtos;
import com.nexus.mobility.entity.Country;
import com.nexus.mobility.entity.CountryStat;
import com.nexus.mobility.service.CountryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping({"/api/countries", "/api/v1/countries"})
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public List<Country> countries() {
        return countryService.countries();
    }

    @GetMapping("/{code}")
    public Country country(@PathVariable String code) {
        return countryService.getCountry(code);
    }

    @GetMapping("/stats")
    public List<CountryStat> stats(@RequestParam UUID tenantId) {
        return countryService.stats(tenantId);
    }

    @PatchMapping("/stats/{countryCode}")
    public CountryStat patchStat(@PathVariable String countryCode,
                                 @RequestParam UUID tenantId,
                                 @RequestBody CountryDtos.CountryStatRequest request) {
        return countryService.patchStat(tenantId, countryCode, request);
    }
}
