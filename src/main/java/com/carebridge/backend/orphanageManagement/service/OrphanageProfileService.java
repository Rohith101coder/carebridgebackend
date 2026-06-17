package com.carebridge.backend.orphanageManagement.service;

// import java.io.IOException;
// import java.net.Authenticator;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;

import com.carebridge.backend.authManagement.entity.User;
import com.carebridge.backend.authManagement.exception.InvalidOTPException;
import com.carebridge.backend.authManagement.exception.OTPEmailNotFoundException;
import com.carebridge.backend.authManagement.exception.OtpAlreadyUsedException;
import com.carebridge.backend.authManagement.exception.OtpExpiredException;
import com.carebridge.backend.authManagement.exception.OtpNotFoundException;
import com.carebridge.backend.authManagement.repository.UserRepository;
import com.carebridge.backend.common.enums.VerificationStatus;
import com.carebridge.backend.common.service.ImageUploadService;
import com.carebridge.backend.needsManagement.exception.CommonException;
// import com.carebridge.backend.donorManagement.exception.FileIssueException;
import com.carebridge.backend.notificationManagement.entity.Otp;
import com.carebridge.backend.notificationManagement.repository.OtpRepository;
import com.carebridge.backend.notificationManagement.service.EmailService;
import com.carebridge.backend.notificationManagement.util.OtpUtil;
import com.carebridge.backend.orphanageManagement.dto.OTPVerifyAndOrpProfileAdd;
import com.carebridge.backend.orphanageManagement.dto.OrpProfileResponse;
import com.carebridge.backend.orphanageManagement.dto.OrphanageProfileRequest;
import com.carebridge.backend.orphanageManagement.dto.OrphanageProfileResponse;
import com.carebridge.backend.orphanageManagement.dto.OrphanageProfileUpdateRequest;
import com.carebridge.backend.orphanageManagement.dto.ResendOrpOTP;
import com.carebridge.backend.orphanageManagement.entity.OrphanageProfile;
import com.carebridge.backend.orphanageManagement.exception.OrpProfileAlreadyExsistException;
import com.carebridge.backend.orphanageManagement.exception.OrphanageProfileNotFoundException;
import com.carebridge.backend.orphanageManagement.exception.UnAuthorizedUserException;
import com.carebridge.backend.orphanageManagement.repository.OrphanageProfileRepository;

// import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrphanageProfileService {
    
    private final UserRepository userRepository;

    private final OrphanageProfileRepository orphanageProfileRepository;

    private final OtpRepository otpRepository;

    private final EmailService emailService;

    private final ImageUploadService imageUploadService;


    public OrpProfileResponse createOrphanageProfile(OrphanageProfileRequest request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userEmail = authentication.getName();

        User user = userRepository.findByEmail(userEmail).orElseThrow(()-> new UnAuthorizedUserException("User not found"));

        if(orphanageProfileRepository.findByUser(user).isPresent()){
            throw new OrpProfileAlreadyExsistException("Profile already exsist");
        }

        OrphanageProfile orphanage = new OrphanageProfile();
        orphanage.setAdminName(request.getAdmin());
        orphanage.setOrphanageName(request.getOrphanageName());
        orphanage.setVillage(request.getVillage());
        orphanage.setMandal(request.getMandal());
        orphanage.setDistrict(request.getDistrict());
        orphanage.setState(request.getState());
        orphanage.setCountry(request.getCountry());
        orphanage.setNumberOfChildren(request.getNumberOfChildren());
        orphanage.setOrphanageEmail(request.getOrphanageEmail());
        orphanage.setOrphanagePhone(request.getOrphanagePhone());
        orphanage.setWebsiteLink(request.getWebsiteLink());
        orphanage.setSocialMediaLinks(request.getSocialMediaLinks());
        orphanage.setDarpanId(request.getDarpanId());
        orphanage.setPanNumber(request.getPanNumber());

       CompletableFuture<String> panPhotoFuture = null;

CompletableFuture<String> jjActFuture = null;

CompletableFuture<String> orphanagePicFuture = null;

CompletableFuture<String> adminPicFuture = null;


            if(request.getPanPhoto() != null
        && !request.getPanPhoto().isEmpty()){

    panPhotoFuture =
            imageUploadService.uploadImageAsync(
                    request.getPanPhoto()
            );
}

    if(request.getJjActCertificatePhoto() != null
        && !request.getJjActCertificatePhoto().isEmpty()){

    jjActFuture =
            imageUploadService.uploadImageAsync(
                    request.getJjActCertificatePhoto()
            );
}

    if(request.getOrphanageProfilePic() != null
        && !request.getOrphanageProfilePic().isEmpty()){

    orphanagePicFuture =
            imageUploadService.uploadImageAsync(
                    request.getOrphanageProfilePic()
            );
}

if(request.getAdminProfilePic() != null
        && !request.getAdminProfilePic().isEmpty()){

    adminPicFuture =
            imageUploadService.uploadImageAsync(
                    request.getAdminProfilePic()
            );
}

// PAN

if(panPhotoFuture != null){

    orphanage.setPanPhoto(
            panPhotoFuture.join()
    );
}

// JJ CERTIFICATE

if(jjActFuture != null){

    orphanage.setJjActCertificatePhoto(
            jjActFuture.join()
    );
}

// ORPHANAGE PIC

if(orphanagePicFuture != null){

    orphanage.setOrphanageProfilePic(
            orphanagePicFuture.join()
    );
}

// ADMIN PIC

if(adminPicFuture != null){

    orphanage.setAdminProfilePic(
            adminPicFuture.join()
    );
}





        String carebridgeId = "CB-ORP"+"-"+Year.now()+"-"+
                UUID.randomUUID().toString().replace("-", "")
                .substring(0,6).toUpperCase();
        
        orphanage.setCarebridgeId(carebridgeId);
        orphanage.setVerificationStatus(VerificationStatus.PENDING);
        orphanage.setOrphanageEmailVerified(false);
        orphanage.setUser(user);


        String otpCode = OtpUtil.generateOtp();

        Otp otp = otpRepository.findByEmail(request.getOrphanageEmail()).orElse(new Otp());

        otp.setEmail(request.getOrphanageEmail());
        otp.setOtp(otpCode);
        otp.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otp.setUsed(false);
        otpRepository.save(otp);

        orphanageProfileRepository.save(orphanage);
        emailService.sendOtp(request.getOrphanageEmail(), otpCode);

        return new OrpProfileResponse("Otp sent to "+request.getOrphanageEmail());
    }

    public OrpProfileResponse OrpOTPVerify(OTPVerifyAndOrpProfileAdd request){

        System.out.println(request.getOrpEmail());
        Otp otp = otpRepository.findByEmail(request.getOrpEmail())
        .orElseThrow(()-> new OtpNotFoundException("OTP not found"));

         if(otp.isUsed()){
                throw new OtpAlreadyUsedException("OTP already used");
            }
            if(otp.getExpiryTime().isBefore(LocalDateTime.now())){
                throw new OtpExpiredException("OTP expired");
            }

            if(!otp.getOtp().equals(request.getOtp())){
                otp.setAttempts(otp.getAttempts()+1);
                otpRepository.save(otp);
                throw new InvalidOTPException("Invalid OTP");
            }

            OrphanageProfile profile = orphanageProfileRepository.findByOrphanageEmail(request.getOrpEmail());

            profile.setOrphanageEmailVerified(true);

            orphanageProfileRepository.save(profile);

            otp.setUsed(true);
            otpRepository.save(otp);
            

            emailService.orpProfileNotification(request.getOrpEmail(), profile.getAdminName());

            return new OrpProfileResponse("Orp Profile Completion Successful Current Status : PENDING");
    }

    public OrpProfileResponse resendOrpOTP(ResendOrpOTP request){
         Otp otp = otpRepository.findByEmail(request.getOrpEmail())
                .orElseThrow(() -> new OTPEmailNotFoundException("No OTP request found"));

        String newOtp = OtpUtil.generateOtp();

        otp.setOtp(newOtp);
        otp.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otp.setUsed(false);

        otpRepository.save(otp);
        emailService.sendOtp(request.getOrpEmail(), newOtp);

        return new OrpProfileResponse("OTP resent successfully to "+request.getOrpEmail());

    }

    public OrpProfileResponse updateOrphanageProfile(OrphanageProfileUpdateRequest request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userEmail = authentication.getName();

        User user = userRepository.findByEmail(userEmail).orElseThrow(()-> new UnAuthorizedUserException("User Not Found"));

        OrphanageProfile profile = orphanageProfileRepository.findByUser(user).orElseThrow(()-> new OrphanageProfileNotFoundException("Orp profile not Found"));

          if(request.getAdminName() != null){
        profile.setAdminName(
                request.getAdminName()
        );
    }

    if(request.getVillage() != null){
        profile.setVillage(
                request.getVillage()
        );
    }

    if(request.getMandal() != null){
        profile.setMandal(
                request.getMandal()
        );
    }

    if(request.getDistrict() != null){
        profile.setDistrict(
                request.getDistrict()
        );
    }

    if(request.getState() != null){
        profile.setState(
                request.getState()
        );
    }

    if(request.getCountry() != null){
        profile.setCountry(
                request.getCountry()
        );
    }

    if(request.getNumberOfChildren() != null){
        profile.setNumberOfChildren(
                request.getNumberOfChildren()
        );
    }

    if(request.getOrphanagePhone() != null){
        profile.setOrphanagePhone(
                request.getOrphanagePhone()
        );
    }

    if(request.getWebsiteLink() != null){
        profile.setWebsiteLink(
                request.getWebsiteLink()
        );
    }

    if(request.getSocialMediaLinks() != null){
        profile.setSocialMediaLinks(
                request.getSocialMediaLinks()
        );
    }

    CompletableFuture<String> orphanagePicFuture = null;

CompletableFuture<String> adminPicFuture = null;


//  orphanage profile pic

if(request.getOrphanageProfilePic() != null
        && !request.getOrphanageProfilePic().isEmpty()){

    orphanagePicFuture =
            imageUploadService.uploadImageAsync(
                    request.getOrphanageProfilePic()
            );
}


//  admin pic

if(request.getAdminProfilePic() != null
        && !request.getAdminProfilePic().isEmpty()){

    adminPicFuture =
            imageUploadService.uploadImageAsync(
                    request.getAdminProfilePic()
            );
}


//  join results

if(orphanagePicFuture != null){

    profile.setOrphanageProfilePic(
            orphanagePicFuture.join()
    );
}

if(adminPicFuture != null){

    profile.setAdminProfilePic(
            adminPicFuture.join()
    );
}
    orphanageProfileRepository.save(profile);



        return new OrpProfileResponse("Orphanage profile updated successfully");
    }


 public OrphanageProfileResponse getOrphanageProfile() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new CommonException("User not found"));

        OrphanageProfile profile =
                orphanageProfileRepository.findByUser(user)
                        .orElseThrow(() ->
                                new CommonException("Orphanage profile not found"));
        if(profile.getVerificationStatus() != VerificationStatus.VERIFIED){
            throw new CommonException("profile not verified");
        }

        return mapToResponse(profile);
    }

     private OrphanageProfileResponse mapToResponse(
            OrphanageProfile profile) {

        return OrphanageProfileResponse.builder()
                .carebridgeId(profile.getCarebridgeId())
                .orphanageName(profile.getOrphanageName())
                .adminName(profile.getAdminName())
                .village(profile.getVillage())
                .mandal(profile.getMandal())
                .district(profile.getDistrict())
                .state(profile.getState())
                .country(profile.getCountry())
                .numberOfChildren(profile.getNumberOfChildren())
                .orphanageEmail(profile.getOrphanageEmail())
                .orphanageEmailVerified(profile.isOrphanageEmailVerified())
                .orphanagePhone(profile.getOrphanagePhone())
                .websiteLink(profile.getWebsiteLink())
                .socialMediaLinks(profile.getSocialMediaLinks())
                .darpanId(profile.getDarpanId())
                .panNumber(profile.getPanNumber())
                .panPhoto(profile.getPanPhoto())
                .jjActCertificatePhoto(profile.getJjActCertificatePhoto())
                .orphanageProfilePic(profile.getOrphanageProfilePic())
                .adminProfilePic(profile.getAdminProfilePic())
                .verificationStatus(profile.getVerificationStatus())
                .createdAt(profile.getCreatedAt())
                .updateAt(profile.getUpdateAt())
                .build();
    }
}
