package com.carebridge.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carebridge.backend.entity.DonorProfile;
import com.carebridge.backend.entity.User;

public interface DonorProfileRepository extends JpaRepository<DonorProfile, Long>{
    
    Optional<User> findByUser(User user);
}
