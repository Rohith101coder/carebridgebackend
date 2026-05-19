package com.carebridge.backend.orphanageManagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.orphanageManagement.dto.OTPVerifyAndOrpProfileAdd;
import com.carebridge.backend.orphanageManagement.dto.OrpProfileResponse;
import com.carebridge.backend.orphanageManagement.dto.OrphanageProfileRequest;
import com.carebridge.backend.orphanageManagement.dto.ResendOrpOTP;
// import com.carebridge.backend.orphanageManagement.repository.OrphanageProfileRepository;
import com.carebridge.backend.orphanageManagement.service.OrphanageProfileService;

// import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orphanage")
@RequiredArgsConstructor
public class OrphanageProfileController {

  private final OrphanageProfileService orphanageProfileService;

    @PostMapping(
        value = "/create-profile",
        consumes = {"multipart/form-data"}
    )
    public ResponseEntity<OrpProfileResponse> createOrpProfile(@Valid @ModelAttribute OrphanageProfileRequest request){
        OrpProfileResponse response = orphanageProfileService.createOrphanageProfile(request);

        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/verifyOrpOTP")
    public ResponseEntity<OrpProfileResponse> verifyOtp(
        @RequestBody OTPVerifyAndOrpProfileAdd request
    ){
        System.out.println(request);
        OrpProfileResponse response = orphanageProfileService.OrpOTPVerify(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resendOrpOTP")
    public ResponseEntity<OrpProfileResponse> resendOrpOTP(@RequestBody ResendOrpOTP request){
        System.out.println(request.getOrpEmail());
        OrpProfileResponse response = orphanageProfileService.resendOrpOTP(request);
        return ResponseEntity.ok(response);
    }


}
