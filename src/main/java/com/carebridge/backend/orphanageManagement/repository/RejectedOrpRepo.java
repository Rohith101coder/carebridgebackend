package com.carebridge.backend.orphanageManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carebridge.backend.orphanageManagement.entity.RejectedOrpProfile;

public interface RejectedOrpRepo extends JpaRepository<RejectedOrpProfile, Long> {
    
}
