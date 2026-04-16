package com.nexus.mobility.dto;

public final class CountryDtos {

    private CountryDtos() {
    }

    public record CountryStatRequest(
            Integer snapshotYear,
            Integer outboundStudents,
            Integer inboundStudents,
            Integer partnershipCount
    ) {
    }
}
