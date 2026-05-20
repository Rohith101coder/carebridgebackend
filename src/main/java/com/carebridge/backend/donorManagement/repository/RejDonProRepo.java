package com.carebridge.backend.donorManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carebridge.backend.donorManagement.entity.RejectedDonorProfile;

public interface RejDonProRepo extends JpaRepository<RejectedDonorProfile,Long>{
    
}
