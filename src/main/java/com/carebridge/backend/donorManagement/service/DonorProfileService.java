package com.carebridge.backend.donorManagement.service;

import java.io.IOException;
import java.time.Year;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.carebridge.backend.authManagement.entity.User;
import com.carebridge.backend.authManagement.repository.UserRepository;
import com.carebridge.backend.common.enums.DonorSubscriptionStatus;
import com.carebridge.backend.common.enums.VerificationStatus;
import com.carebridge.backend.common.service.ImageUploadService;
import com.carebridge.backend.donorManagement.dto.DonorProfileRequest;
import com.carebridge.backend.donorManagement.dto.DonorProfileUpdateRequest;
import com.carebridge.backend.donorManagement.dto.DonorResponse;
import com.carebridge.backend.donorManagement.entity.DonorProfile;
import com.carebridge.backend.donorManagement.exception.DonorProfileAlreadyExsistException;
import com.carebridge.backend.donorManagement.exception.DonorProfileNotFoundException;
import com.carebridge.backend.donorManagement.exception.FileIssueException;
import com.carebridge.backend.donorManagement.exception.UserNotFoundException;
import com.carebridge.backend.donorManagement.repository.DonorProfileRepository;
import com.carebridge.backend.notificationManagement.service.EmailService;

// import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DonorProfileService {

    private final UserRepository userRepository;
    
    private final DonorProfileRepository donorProfileRepository;

    private final EmailService emailService;

    private final ImageUploadService imageUploadService;

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

            String donorId = "CB-DON-"+Year.now().getValue()+"-"+UUID.randomUUID().toString().replace("-", "")
            .substring(0,6).toUpperCase();
                String profilePic = imageUploadService.uploadImage(request.getProfilePic());
                donor.setProfilePic(profilePic);

                String panPhotoUrl = imageUploadService.uploadImage(request.getPanPhoto());
                donor.setPanPhoto(panPhotoUrl);

            donor.setCareBridgeID(donorId);
            donor.setDonorStatus(VerificationStatus.PENDING);

            donor.setSubscriptionStatus(DonorSubscriptionStatus.UNSUBSCRIBED);
           
            donor.setUser(user);

            donorProfileRepository.save(donor);

            emailService.donorProfileNotification(email, request.getName());

            return new DonorResponse("Donor profile created successfully Status : PENDING");

    }

    public DonorResponse updateDonorProfile(
        DonorProfileUpdateRequest request
    ){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User not found"));

        DonorProfile donor = donorProfileRepository.findByUser(user).orElseThrow(()-> new DonorProfileNotFoundException("Donor profile not found"));

        if(request.getDesignation() != null){
            donor.setDesignation(request.getDesignation());
        }

        if(request.getHouseNum()!=null){
            donor.setHouseNum(request.getHouseNum());
        }


          if (request.getVillage() != null) {
            donor.setVillage(
                    request.getVillage()
            );
        }

        if (request.getMandal() != null) {
            donor.setMandal(
                    request.getMandal()
            );
        }

          if (request.getDistrict() != null) {
            donor.setDistrict(
                    request.getDistrict()
            );
        }

        if (request.getState() != null) {
            donor.setState(
                    request.getState()
            );
        }

         if (request.getCountry() != null) {
            donor.setCountry(
                    request.getCountry()
            );
        }

        if (request.getPhone() != null) {
            donor.setPhone(
                    request.getPhone()
            );
        }

         // 📷 update profile pic
        if (request.getProfilePic() != null
                && !request.getProfilePic().isEmpty()) {

           donor.setProfilePic(imageUploadService.uploadImage(request.getProfilePic()));
        }

        // 📷 update pan photo
        if (request.getPanPhoto() != null
                && !request.getPanPhoto().isEmpty()) {

            donor.setPanPhoto(imageUploadService.uploadImage(request.getPanPhoto()));
        }

        donorProfileRepository.save(donor);


        return new DonorResponse("Donor Profile updated successfully");
    }

}
