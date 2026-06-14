package com.carebridge.backend.landingPagedata.service;

import org.springframework.stereotype.Service;

import com.carebridge.backend.common.enums.VerificationStatus;
import com.carebridge.backend.donationManagement.enums.DonationStatus;
import com.carebridge.backend.donationManagement.repository.DonationRequestRepo;
import com.carebridge.backend.donorManagement.repository.DonorProfileRepository;
import com.carebridge.backend.landingPagedata.dto.LandingStatsResponse;
import com.carebridge.backend.orphanageManagement.repository.OrphanageProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LandingPageService{

    private final DonorProfileRepository donor;
    private final OrphanageProfileRepository orphanage;
    private final DonationRequestRepo donation;

    public LandingStatsResponse getStats(){

         long verifiedOrphanages =
                orphanage.countByVerificationStatus(VerificationStatus.VERIFIED);

        long activeDonors =
               donor.count();

        long needsFulfilled =
                donation.countByDonationStatus(DonationStatus.DELIVERED);

        // Temporary value
        long citiesReached = orphanage.countDistinctDistricts();

        return new LandingStatsResponse(
                verifiedOrphanages,
                activeDonors,
                needsFulfilled,
                citiesReached
        );
    }
    
}
