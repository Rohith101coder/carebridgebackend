package com.carebridge.backend.donationManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carebridge.backend.donationManagement.entity.ItemDeliveredResponse;

public interface ItemDeliveredRepo extends JpaRepository<ItemDeliveredResponse, Long> {
    
}
