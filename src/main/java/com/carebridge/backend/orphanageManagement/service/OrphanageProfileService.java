package com.carebridge.backend.orphanageManagement.service;

import java.io.IOException;
import java.net.Authenticator;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.carebridge.backend.authManagement.entity.User;
import com.carebridge.backend.authManagement.exception.InvalidOTPException;
import com.carebridge.backend.authManagement.exception.OtpAlreadyUsedException;
import com.carebridge.backend.authManagement.exception.OtpExpiredException;
import com.carebridge.backend.authManagement.exception.OtpNotFoundException;
import com.carebridge.backend.authManagement.repository.UserRepository;
import com.carebridge.backend.common.enums.VerificationStatus;
import com.carebridge.backend.donorManagement.exception.FileIssueException;
import com.carebridge.backend.notificationManagement.entity.Otp;
import com.carebridge.backend.notificationManagement.repository.OtpRepository;
import com.carebridge.backend.notificationManagement.service.EmailService;
import com.carebridge.backend.notificationManagement.util.OtpUtil;
import com.carebridge.backend.orphanageManagement.dto.OTPVerifyAndOrpProfileAdd;
import com.carebridge.backend.orphanageManagement.dto.OrpProfileResponse;
import com.carebridge.backend.orphanageManagement.dto.OrphanageProfileRequest;
import com.carebridge.backend.orphanageManagement.entity.OrphanageProfile;
import com.carebridge.backend.orphanageManagement.exception.OrpProfileAlreadyExsistException;
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

        MultipartFile panPhoto = request.getPanPhoto();
        if(panPhoto!=null && !panPhoto.isEmpty()){
            try{
                orphanage.setPanPhoto(panPhoto.getBytes());
            }catch(IOException e){
                throw new FileIssueException("Fail to upload file");
            }
        }

        MultipartFile jjActCertificatePhoto = request.getJjActCertificatePhoto();
        if(jjActCertificatePhoto!=null && !jjActCertificatePhoto.isEmpty()){
             try{
                orphanage.setJjActCertificatePhoto(jjActCertificatePhoto.getBytes());
            }catch(IOException e){
                throw new FileIssueException("Fail to upload file");
            }
        }

        MultipartFile orphanageProfilePic = request.getOrphanageProfilePic();
        if(orphanageProfilePic!=null && !orphanageProfilePic.isEmpty()){
             try{
                orphanage.setOrphanageProfilePic(orphanageProfilePic.getBytes());
            }catch(IOException e){
                throw new FileIssueException("Fail to upload file");
            }
        }

        MultipartFile adminPic = request.getAdminProfilePic();

        if(adminPic!=null && !adminPic.isEmpty()){
            try{
                orphanage.setOrphanageProfilePic(adminPic.getBytes());
            }catch(IOException e){
                throw new FileIssueException("Fail to upload file");
            }
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

        Otp otp = otpRepository.findByEmail(request.getEmail())
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

            OrphanageProfile profile = orphanageProfileRepository.findByEmail(request.getEmail());

            profile.setOrphanageEmailVerified(true);

            orphanageProfileRepository.save(profile);

            otp.setUsed(true);
            otpRepository.save(otp);

            // emailService.

            return new OrpProfileResponse("Orp Profile Completion Successful Current Status : PENDING");



    }
}
