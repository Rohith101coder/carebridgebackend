package com.carebridge.backend.adminManagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.adminManagement.service.AdminService;
import com.carebridge.backend.donorManagement.entity.DonorProfile;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/donor")
@RequiredArgsConstructor
public class AdminDonorController {
    
    private final AdminService adminService;

    @GetMapping("/pending")
    public ResponseEntity<List<DonorProfile>> getAllProfiles(){
        
        List<DonorProfile> list = adminService.getAllPendingDonorProfiles();

        return ResponseEntity.ok(list);
    }
}
