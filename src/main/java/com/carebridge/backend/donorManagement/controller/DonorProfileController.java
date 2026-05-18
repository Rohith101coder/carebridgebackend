package com.carebridge.backend.donorManagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.donorManagement.dto.DonorProfileRequest;
import com.carebridge.backend.donorManagement.dto.DonorProfileUpdateRequest;
import com.carebridge.backend.donorManagement.dto.DonorResponse;
import com.carebridge.backend.donorManagement.service.DonorProfileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/donor")
@RequiredArgsConstructor
@Validated
public class DonorProfileController {

    private final DonorProfileService donorProfileService;

    @PostMapping(
        value = "/create-profile",
        consumes = {"multipart/form-data"}
    )

    public ResponseEntity<DonorResponse> createDonorProfile(
        @Valid @ModelAttribute DonorProfileRequest request
    ){

        DonorResponse response = donorProfileService.createDonorProfile(request);

        return ResponseEntity.ok(response);
    }

    
    @PutMapping(
        value = "/update-profile",
        consumes = {"multipart/form-data"}
    )
    public ResponseEntity<DonorResponse> updateDonorProfile(
        @ModelAttribute DonorProfileUpdateRequest request
    ){
        DonorResponse response = donorProfileService.updateDonorProfile(request);
        return ResponseEntity.ok(response);
    }
}
