package com.carebridge.backend.dashBoardManagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.dashBoardManagement.dto.DonorDashBoard;
import com.carebridge.backend.dashBoardManagement.service.DonorDashBoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/donor")
public class DonorDashBoardController {
    
    private final DonorDashBoardService donorDashBoardService;

    @GetMapping("/dashboard")
    public ResponseEntity<DonorDashBoard> getDonorDashBoard(){
        DonorDashBoard donorDashBoard = donorDashBoardService.getDonorDashBoard();
        return ResponseEntity.ok(donorDashBoard);
    }

    
}
