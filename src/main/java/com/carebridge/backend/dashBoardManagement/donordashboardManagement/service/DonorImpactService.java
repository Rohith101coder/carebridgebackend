package com.carebridge.backend.dashBoardManagement.donordashboardManagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.carebridge.backend.donationManagement.repository.DonationRequestRepo;
import com.carebridge.backend.orphanageManagement.repository.OrphanageProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DonorImpactService {

    private final DonationRequestRepo donationRequestRepo;
    private final OrphanageProfileRepository orphanageProfileRepository;
    

    public int totalChildrenImpacted(String donorId){

        List<String> orpNames = donationRequestRepo.getOrpNames(donorId);
        int totalChildren = 0;
        for(String orpName : orpNames){
            int childrens = orphanageProfileRepository.getChildrenCount(orpName);
            totalChildren = totalChildren + childrens;
        }
        return totalChildren;
    }
}
