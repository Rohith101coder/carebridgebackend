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

    @GetMapping("/approved")
    public ResponseEntity<List<OrphanageProfile>> getAllApproved(){

        List<OrphanageProfile> list = adminService.getAllApprovedOrpProfiles();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/rejected")
    public ResponseEntity<List<OrphanageProfile>> getAllRejected(){

        List<OrphanageProfile> list = adminService.getAllRejectedOrpProfiles();

        return ResponseEntity.ok(list);
    }

    
}
