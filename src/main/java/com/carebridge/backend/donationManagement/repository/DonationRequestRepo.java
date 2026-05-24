package com.carebridge.backend.donationManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carebridge.backend.donationManagement.entity.DonationRequest;

public interface DonationRequestRepo extends JpaRepository<DonationRequest, Long> {
    
}
