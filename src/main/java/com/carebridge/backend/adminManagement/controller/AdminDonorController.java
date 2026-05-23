package com.carebridge.backend.adminManagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.adminManagement.dto.AdminResponse;
import com.carebridge.backend.adminManagement.dto.EmailReason;
import com.carebridge.backend.adminManagement.service.AdminService;
import com.carebridge.backend.donorManagement.entity.DonorProfile;

// import io.swagger.v3.oas.annotations.parameters.RequestBody;
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

    @GetMapping("/approved")
    public ResponseEntity<List<DonorProfile>> getAllApprovedProfiles(){

        List<DonorProfile> list = adminService.getAllApprovedDonorProfile();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/rejected")
    public ResponseEntity<List<DonorProfile>> getAllRejectedProfiles(){
        List<DonorProfile> list = adminService.getAllRejectedDonorProfiles();
        return ResponseEntity.ok(list);
    }

    @PatchMapping("/approve/{id}")
    public ResponseEntity<AdminResponse> approveDonor(@PathVariable String id){
        
        AdminResponse response = adminService.approveDonor(id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/reject/{id}")
    public ResponseEntity<AdminResponse> rejectDonor(@PathVariable String id, @RequestBody EmailReason reason){

         System.out.println(reason.getReason());
        AdminResponse response = adminService.rejectDonor(id, reason);

        return ResponseEntity.ok(response);
    }
}
