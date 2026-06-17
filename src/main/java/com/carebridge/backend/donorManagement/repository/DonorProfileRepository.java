package com.carebridge.backend.donorManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carebridge.backend.authManagement.entity.User;
import com.carebridge.backend.donorManagement.entity.DonorProfile;
import java.util.List;
import com.carebridge.backend.common.enums.VerificationStatus;


public interface DonorProfileRepository extends JpaRepository<DonorProfile, Long>{
    
    Optional<DonorProfile> findByUser(User user);
    List<DonorProfile> findByDonorStatus(VerificationStatus donorStatus);

    Optional<DonorProfile> findByCareBridgeID(String careBridgeID);

    void deleteByCareBridgeID(String id);


    // Optional<DonorProfile> findByCareBridgeID(String careBridgeID);
}
