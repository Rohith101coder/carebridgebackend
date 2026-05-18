package com.carebridge.backend.orphanageManagement.service;

import java.net.Authenticator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.carebridge.backend.authManagement.entity.User;
import com.carebridge.backend.authManagement.repository.UserRepository;
import com.carebridge.backend.notificationManagement.service.EmailService;
import com.carebridge.backend.orphanageManagement.dto.OrphanageProfileRequest;
import com.carebridge.backend.orphanageManagement.repository.OrphanageProfileRepository;

import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrphanageProfileService {
    
    private final UserRepository userRepository;

    private final OrphanageProfileRepository orphanageProfileRepository;

    private EmailService emailService;

    public String createOrphanageProfile(OrphanageProfileRequest request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));








        return null;
    }
}
