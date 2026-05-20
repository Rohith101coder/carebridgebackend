package com.carebridge.backend.adminManagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.carebridge.backend.adminManagement.dto.EmailReason;
import com.carebridge.backend.adminManagement.dto.AdminResponse;
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

    @PatchMapping("/approve/{id}")
    public ResponseEntity<AdminResponse> approveOrp(@PathVariable String id){

        AdminResponse response = adminService.approveOrp(id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/reject/{id}")
    public ResponseEntity<AdminResponse> rejectOrp(@PathVariable String id, @RequestBody EmailReason reason){

       
        AdminResponse response = adminService.rejectOrp(id,reason);

        return ResponseEntity.ok(response);
    }

    
}
