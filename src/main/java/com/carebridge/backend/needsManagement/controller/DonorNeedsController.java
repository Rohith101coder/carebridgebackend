package com.carebridge.backend.needsManagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.needsManagement.entity.NeedItem;
import com.carebridge.backend.needsManagement.service.NeedItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/donor")
@RequiredArgsConstructor
public class DonorNeedsController {
    
    private final NeedItemService needItemService;


    @GetMapping("/allNeeds")
    public ResponseEntity<List<NeedItem>> getAllNeeds(){

        List<NeedItem> needs = needItemService.getAllNeeds();

        return ResponseEntity.ok(needs);
    }
}
