package com.carebridge.backend.donationManagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.donationManagement.dto.DonationRequestDTO;
import com.carebridge.backend.donationManagement.dto.DonationResponse;
import com.carebridge.backend.donationManagement.service.DonationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/donor")
@RequiredArgsConstructor
public class DonationController {

    private final DonationService donationService;
    
@PostMapping(
    value = "/donate",
    consumes = {"multipart/form-data"}
)
public ResponseEntity<DonationResponse> donate(
        @ModelAttribute DonationRequestDTO request
){

   DonationResponse response = donationService.createDonationRequest(request);

    return ResponseEntity.ok(
          response
    );
}

}
