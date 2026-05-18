package com.carebridge.backend.donorManagement.service;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.carebridge.backend.authManagement.entity.User;
import com.carebridge.backend.authManagement.repository.UserRepository;
import com.carebridge.backend.common.enums.DonorSubscriptionStatus;
import com.carebridge.backend.common.enums.VerificationStatus;
import com.carebridge.backend.donorManagement.dto.DonorProfileRequest;
import com.carebridge.backend.donorManagement.entity.DonorProfile;
import com.carebridge.backend.donorManagement.repository.DonorProfileRepository;

// import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DonorProfileService {

    private final UserRepository userRepository;
    
    private final DonorProfileRepository donorProfileRepository;

    public String createDonorProfile(DonorProfileRequest request){

        try{

            Authentication authentication = SecurityContextHolder
                                            .getContext()
                                            .getAuthentication();

            String email = authentication.getName();

            User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));

                if(donorProfileRepository.findByUser(user).isPresent()){
                    throw new RuntimeException("Donor profile already exists");
                }

            DonorProfile donor = new DonorProfile();

            donor.setName(request.getName());
            donor.setDateOfBirth(request.getDob());
            donor.setDesignation(request.getDesignation());
            donor.setHouseNum(request.getHouseNum());
            donor.setVillage(request.getVillage());
            donor.setMandal(request.getMandal());
            donor.setDistrict(request.getDistrict());
            donor.setCountry(request.getCountry());
            donor.setState(request.getState());
            donor.setPhone(request.getPhone());
            donor.setPanNumber(request.getPanNumber());

            MultipartFile profilePic = request.getProfilePic();
            if(profilePic != null && !profilePic.isEmpty()){
                 donor.setProfilePic(profilePic.getBytes());
            }

            MultipartFile panPhoto = request.getPanPhoto();

            if(panPhoto!=null && !panPhoto.isEmpty()){
                donor.setPanPhoto(panPhoto.getBytes());
            }

            donor.setDonorStatus(VerificationStatus.PENDING);

            donor.setSubscriptionStatus(DonorSubscriptionStatus.UNSUBSCRIBED);
           
            donor.setUser(user);

            donorProfileRepository.save(donor);

            return "Donor profile created successfully";

        }catch(IOException e){
            throw new RuntimeException("Failed to process images");
        }



    }


}
