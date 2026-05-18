package com.carebridge.backend.orphanageManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carebridge.backend.orphanageManagement.entity.OrphanageProfile;

public interface OrphanageProfileRepository extends JpaRepository<OrphanageProfile, Long>{
    
}
