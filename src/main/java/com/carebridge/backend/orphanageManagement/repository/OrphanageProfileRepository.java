package com.carebridge.backend.orphanageManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carebridge.backend.orphanageManagement.entity.OrphanageProfile;
// import java.util.List;
import java.util.Optional;

import com.carebridge.backend.authManagement.entity.User;
import java.util.List;
import com.carebridge.backend.common.enums.VerificationStatus;



public interface OrphanageProfileRepository extends JpaRepository<OrphanageProfile, Long>{
    Optional<OrphanageProfile>  findByUser(User user);
    OrphanageProfile findByOrphanageEmail(String orphanageEmail);

    List<OrphanageProfile> findByVerificationStatus(VerificationStatus verificationStatus);

    Optional<OrphanageProfile>  findByCarebridgeId(String carebridgeId);

    void deleteByCarebridgeId(String id);

    // Optional<OrphanageProfile> findByCareBridgeId(String id);
}
