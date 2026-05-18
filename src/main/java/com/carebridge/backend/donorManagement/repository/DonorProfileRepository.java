package com.carebridge.backend.donorManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carebridge.backend.authManagement.entity.User;
import com.carebridge.backend.donorManagement.entity.DonorProfile;

public interface DonorProfileRepository extends JpaRepository<DonorProfile, Long>{
    
    Optional<User> findByUser(User user);
}
