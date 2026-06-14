package com.carebridge.backend.landingPagedata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LandingStatsResponse{

    private long verifiedOrphanages;
    private long activeDonors;
    private long needsFulfilled;
    private long citiesReached;
}
