package com.carebridge.backend.notificationManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carebridge.backend.notificationManagement.entity.Otp;

public interface OtpRepository extends JpaRepository<Otp, Long>{
    Optional<Otp> findByEmail(String email);
}
