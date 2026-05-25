package com.carebridge.backend.donationManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carebridge.backend.donationManagement.entity.DonationRequest;
import com.carebridge.backend.donationManagement.enums.DonationStatus;

import java.util.List;


public interface DonationRequestRepo extends JpaRepository<DonationRequest, Long> {
    
    List<DonationRequest> findByDonorCareBridgeIdAndDonationStatus(String donorCareBridgeId, DonationStatus donationStatus);
}
