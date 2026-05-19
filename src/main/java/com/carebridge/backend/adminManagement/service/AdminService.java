package com.carebridge.backend.adminManagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.carebridge.backend.common.enums.VerificationStatus;
import com.carebridge.backend.donorManagement.entity.DonorProfile;
import com.carebridge.backend.donorManagement.repository.DonorProfileRepository;
import com.carebridge.backend.orphanageManagement.entity.OrphanageProfile;
import com.carebridge.backend.orphanageManagement.repository.OrphanageProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
    
    private final DonorProfileRepository donorProfileRepository;

    private final OrphanageProfileRepository orphanageProfileRepository;


    public List<DonorProfile> getAllPendingDonorProfiles(){

        List<DonorProfile> profileList = donorProfileRepository.findByDonorStatus(VerificationStatus.PENDING);
        // List<DonorProfile> profileList = donorProfileRepository.findAll();

        return profileList;
    }

    public List<OrphanageProfile> getAllPendingOrphanageProfiles(){

        List<OrphanageProfile> orpPendingList = orphanageProfileRepository.findByVerificationStatus(VerificationStatus.PENDING);

        return orpPendingList;
    }
}
