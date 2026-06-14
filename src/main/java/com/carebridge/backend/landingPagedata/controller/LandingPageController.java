package com.carebridge.backend.landingPagedata.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.landingPagedata.dto.LandingStatsResponse;
import com.carebridge.backend.landingPagedata.service.LandingPageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LandingPageController {

    private final LandingPageService landingPageService;

    @GetMapping("/api/landing/stats")
    public LandingStatsResponse getStats() {
        return landingPageService.getStats();
    }
}