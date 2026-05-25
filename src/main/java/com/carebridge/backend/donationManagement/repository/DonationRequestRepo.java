package com.carebridge.backend.donationManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carebridge.backend.donationManagement.entity.DonationRequest;
import com.carebridge.backend.donationManagement.enums.DonationStatus;

import java.util.List;
import java.util.Optional;


public interface DonationRequestRepo extends JpaRepository<DonationRequest, Long> {
    
    List<DonationRequest> findByDonorCareBridgeIdAndDonationStatus(String donorCareBridgeId, DonationStatus donationStatus);

    Optional<DonationRequest> findByDonationRequestIdAndDonorCareBridgeId(String donationId, String donorId);
}
