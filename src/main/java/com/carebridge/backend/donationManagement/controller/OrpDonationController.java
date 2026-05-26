package com.carebridge.backend.donationManagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.donationManagement.dto.DonationResponse;
import com.carebridge.backend.donationManagement.dto.ItemDeliveredDTO;
import com.carebridge.backend.donationManagement.dto.OrphanageDonationResponse;
import com.carebridge.backend.donationManagement.service.OrphanageDonationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orphanage")
@RequiredArgsConstructor
public class OrpDonationController {
    

    private final OrphanageDonationService orphanageDonationService;


    @GetMapping("/pending-donations")
    public ResponseEntity<List<OrphanageDonationResponse>> getPendings(){

        List<OrphanageDonationResponse> pendings = orphanageDonationService.getPendingDonations();

        return ResponseEntity.ok(pendings);
    }

     @GetMapping("/delivered-donations")
    public ResponseEntity<List<OrphanageDonationResponse>> getDelivers(){

        List<OrphanageDonationResponse> delivers = orphanageDonationService.getDeliveredDonations();

        return ResponseEntity.ok(delivers);
    }

     @GetMapping("/cancelled-donations")
    public ResponseEntity<List<OrphanageDonationResponse>> getCancelles(){

        List<OrphanageDonationResponse> cancels = orphanageDonationService.getCancelledDonations();

        return ResponseEntity.ok(cancels);
    }


    @PostMapping(value = "/delivered/{id}",
    consumes = {"multipart/form-data"})
    public ResponseEntity<DonationResponse> acceptDonation(@PathVariable String id, @ModelAttribute ItemDeliveredDTO itemResponse){

        DonationResponse response = orphanageDonationService.acceptDonation(id, itemResponse);
        return ResponseEntity.ok(response);
    } 


}
