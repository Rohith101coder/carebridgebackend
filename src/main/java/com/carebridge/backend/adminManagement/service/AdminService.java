package com.carebridge.backend.adminManagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.carebridge.backend.adminManagement.dto.AdminResponse;
import com.carebridge.backend.adminManagement.dto.EmailReason;
import com.carebridge.backend.adminManagement.exception.DonorProfileNotFoundException;
import com.carebridge.backend.adminManagement.exception.OrpException;
import com.carebridge.backend.authManagement.entity.User;
import com.carebridge.backend.common.enums.VerificationStatus;
import com.carebridge.backend.donorManagement.entity.DonorProfile;
import com.carebridge.backend.donorManagement.repository.DonorProfileRepository;
import com.carebridge.backend.notificationManagement.service.EmailService;
import com.carebridge.backend.orphanageManagement.entity.OrphanageProfile;
import com.carebridge.backend.orphanageManagement.repository.OrphanageProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
    
    private final DonorProfileRepository donorProfileRepository;

    private final OrphanageProfileRepository orphanageProfileRepository;

    private final EmailService emailService;


    public List<DonorProfile> getAllPendingDonorProfiles(){

        List<DonorProfile> profileList = donorProfileRepository.findByDonorStatus(VerificationStatus.PENDING);
        // List<DonorProfile> profileList = donorProfileRepository.findAll();

        return profileList;
    }

    public List<OrphanageProfile> getAllPendingOrphanageProfiles(){

        List<OrphanageProfile> orpPendingList = orphanageProfileRepository.findByVerificationStatus(VerificationStatus.PENDING);

        return orpPendingList;
    }

    public List<DonorProfile> getAllApprovedDonorProfile(){

        List<DonorProfile> profiles = donorProfileRepository.findByDonorStatus(VerificationStatus.VERIFIED);

        return profiles;
    }

    public List<OrphanageProfile> getAllApprovedOrpProfiles(){
        List<OrphanageProfile> profiles = orphanageProfileRepository.findByVerificationStatus(
            VerificationStatus.VERIFIED
        );

        return profiles;
    }


    public List<DonorProfile> getAllRejectedDonorProfiles(){
        List<DonorProfile> profiles = donorProfileRepository.findByDonorStatus(VerificationStatus.REJECTED);
        return profiles;
    }

    public List<OrphanageProfile> getAllRejectedOrpProfiles(){
        List<OrphanageProfile> profiles = orphanageProfileRepository.findByVerificationStatus(
            VerificationStatus.REJECTED
        );

        return profiles;
    }


    public AdminResponse approveDonor(String id){

        DonorProfile profile = donorProfileRepository.findByCareBridgeID(id).orElseThrow(()-> new DonorProfileNotFoundException("No Profile Found"));

        profile.setDonorStatus(VerificationStatus.VERIFIED);

        donorProfileRepository.save(profile);

        User user = profile.getUser();
        String email = user.getEmail();
        String name = profile.getName();

        emailService.donorApproveNotification(email, name);

        return new AdminResponse("Donor approval success");
    }


    public AdminResponse rejectDonor(String id, EmailReason reason){

         DonorProfile profile = donorProfileRepository.findByCareBridgeID(id).orElseThrow(()-> new DonorProfileNotFoundException("No Profile Found"));

         profile.setDonorStatus(VerificationStatus.REJECTED);

         donorProfileRepository.save(profile);

          User user = profile.getUser();
        String email = user.getEmail();
        String name = profile.getName();
        
        emailService.donorRejectionNotification(email,reason.getReason(),name);
        return new AdminResponse("Donor Rejected");
    }

    public AdminResponse approveOrp(String id){

        OrphanageProfile profile = orphanageProfileRepository.findByCarebridgeId(id).orElseThrow(()-> new OrpException("Orp not found"));

        profile.setVerificationStatus(VerificationStatus.VERIFIED);

        String orpEmail = profile.getOrphanageEmail();
        String orpAdmin = profile.getAdminName();
        String orpName = profile.getOrphanageName();

        emailService.approveOrpNotification(orpEmail, orpAdmin, orpName);

        return new AdminResponse("Orp approved");
    }

    public AdminResponse rejectOrp(String id, EmailReason reason){
         OrphanageProfile profile = orphanageProfileRepository.findByCarebridgeId(id).orElseThrow(()-> new OrpException("Orp not found"));

           profile.setVerificationStatus(VerificationStatus.REJECTED);
            String orpEmail = profile.getOrphanageEmail();
        String orpAdmin = profile.getAdminName();
        String orpName = profile.getOrphanageName();

        emailService.rejectOrpNotification(orpEmail, orpAdmin, orpName, reason.getReason());

           return new AdminResponse("Orp rejected");
    }
}
