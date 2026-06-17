package com.carebridge.backend.dashBoardManagement.orphanagedashboardManagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.dashBoardManagement.orphanagedashboardManagement.dto.DashboardResponse;
import com.carebridge.backend.dashBoardManagement.orphanagedashboardManagement.service.OrphanageDashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orphanage")
@RequiredArgsConstructor
public class OrphanageDashboardController {

    private final OrphanageDashboardService orphanageDashboardService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard() {

        DashboardResponse response =
                orphanageDashboardService.getDashboard();

        return ResponseEntity.ok(response);
    }
}