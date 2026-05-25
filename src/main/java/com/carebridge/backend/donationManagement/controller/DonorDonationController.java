package com.carebridge.backend.donationManagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.donationManagement.dto.DonationRequestDTO;
import com.carebridge.backend.donationManagement.dto.DonationResponse;
import com.carebridge.backend.donationManagement.service.DonationService;
import com.carebridge.backend.donationManagement.service.DonorDonationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/donor")
@RequiredArgsConstructor
public class DonorDonationController {

    private final DonationService donationService;
    private final DonorDonationService donorDonationService;
    
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


@GetMapping("/pending-donations")
    public ResponseEntity<List<DonationRequestDTO>> getMyPendingDonations(){

        List<DonationRequestDTO> pendings = donorDonationService.getMyPendingDonations();

        return ResponseEntity.ok(pendings);
    }

    @GetMapping("/completed-donations")
    public ResponseEntity<List<DonationRequestDTO>> getMyCompletedDonations(){

        List<DonationRequestDTO> completed = donorDonationService.getMyCompletedDonations();

        return ResponseEntity.ok(completed);
    }

}
