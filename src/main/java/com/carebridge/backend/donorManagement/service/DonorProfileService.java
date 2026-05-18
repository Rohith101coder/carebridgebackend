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
import com.carebridge.backend.donorManagement.dto.DonorResponse;
import com.carebridge.backend.donorManagement.entity.DonorProfile;
import com.carebridge.backend.donorManagement.exception.DonorProfileAlreadyExsistException;
import com.carebridge.backend.donorManagement.exception.FileIssueException;
import com.carebridge.backend.donorManagement.exception.UserNotFoundException;
import com.carebridge.backend.donorManagement.repository.DonorProfileRepository;

// import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DonorProfileService {

    private final UserRepository userRepository;
    
    private final DonorProfileRepository donorProfileRepository;

    public DonorResponse createDonorProfile(DonorProfileRequest request){

        

            Authentication authentication = SecurityContextHolder
                                            .getContext()
                                            .getAuthentication();

            String email = authentication.getName();

            User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User not found"));

                if(donorProfileRepository.findByUser(user).isPresent()){
                    throw new DonorProfileAlreadyExsistException("Donor profile already exists");
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
                 try {
                    donor.setProfilePic(profilePic.getBytes());
                 } catch (IOException e) {
                  throw new FileIssueException("Fail to upload File");
                 }
            }

            MultipartFile panPhoto = request.getPanPhoto();

            if(panPhoto!=null && !panPhoto.isEmpty()){
                try {
                    donor.setPanPhoto(panPhoto.getBytes());
                } catch (IOException e) {
                   throw new FileIssueException("Fail to upload File");
                }
            }

            donor.setDonorStatus(VerificationStatus.PENDING);

            donor.setSubscriptionStatus(DonorSubscriptionStatus.UNSUBSCRIBED);
           
            donor.setUser(user);

            donorProfileRepository.save(donor);

            return new DonorResponse("Donor profile created successfully");

       



    }


}
