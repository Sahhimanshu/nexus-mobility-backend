package com.nexus.mobility.repository;

import com.nexus.mobility.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String> {
}
