package com.carebridge.backend.adminManagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.adminManagement.service.AdminService;
import com.carebridge.backend.orphanageManagement.entity.OrphanageProfile;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/orphanage")
@RequiredArgsConstructor
public class AdminOrpController {
    private final AdminService adminService;


    @GetMapping("/pending")
    public ResponseEntity<List<OrphanageProfile>> getAllPendings(){
        
        List<OrphanageProfile> list = adminService.getAllPendingOrphanageProfiles();

        return ResponseEntity.ok(list);
    }
}
