package com.carebridge.backend.donationManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carebridge.backend.donationManagement.entity.ItemDeliveredResponse;

public interface ItemDeliveredRepo extends JpaRepository<ItemDeliveredResponse, Long> {
    List<ItemDeliveredResponse> findTop5ByOrpIdOrderByCreatedAtDesc(
        String orpId
);
}
