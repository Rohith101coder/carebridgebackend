package com.carebridge.backend.donationManagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.donationManagement.dto.DonationDTO;
import com.carebridge.backend.donationManagement.dto.DonationRequestDTO;
import com.carebridge.backend.donationManagement.dto.DonationResponse;
import com.carebridge.backend.donationManagement.dto.DonationUpdateRequest;
import com.carebridge.backend.donationManagement.service.DonationService;
import com.carebridge.backend.donationManagement.service.DonorDonationService;

import jakarta.validation.Valid;
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
    public ResponseEntity<List<DonationDTO>> getMyPendingDonations(){

        List<DonationDTO> pendings = donorDonationService.getMyPendingDonations();

        return ResponseEntity.ok(pendings);
    }


    @GetMapping("/completed-donations")
    public ResponseEntity<List<DonationDTO>> getMyCompletedDonations(){

        List<DonationDTO> completed = donorDonationService.getMyCompletedDonations();

        return ResponseEntity.ok(completed);
    }


    @DeleteMapping("/delete-pending-donation/{id}")
    public ResponseEntity<DonationResponse> deleteDonation(@PathVariable String id){
        DonationResponse response = donorDonationService.deleteDonation(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/cancelled-donations")
    public ResponseEntity<List<DonationDTO>> getMyCancelledDonations(){

        List<DonationDTO> cancelled = donorDonationService.getMyCancelledDonations();

        return ResponseEntity.ok(cancelled);
    }

    @GetMapping("/delivered-donations")
    public ResponseEntity<List<DonationDTO>> getMyDeliveredDonations(){

        List<DonationDTO> delivered = donorDonationService.getMyCancelledDonations();

        return ResponseEntity.ok(delivered);
    }

    @PatchMapping(
        value = "/update/{donationId}",
        consumes = {"multipart/form-data"}
)
public ResponseEntity<DonationResponse> updateDonation(

        @PathVariable
        String donationId,

        @ModelAttribute
        DonationUpdateRequest request
){

    DonationResponse response =
            donorDonationService.updateDonation(
                    request,
                    donationId
            );

    return ResponseEntity.ok(response);
}



}
