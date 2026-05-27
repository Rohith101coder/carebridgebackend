package com.carebridge.backend.visitbookingManagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.visitbookingManagement.dto.DonorSlot;
import com.carebridge.backend.visitbookingManagement.service.DonorSlotService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/donor")
@RequiredArgsConstructor
public class DonorSlotController {
    
    private final DonorSlotService donorSlotService;


    @GetMapping("/available-slots/{id}")
    public ResponseEntity<List<DonorSlot>> getAvailableSlots(@PathVariable String id){

        List<DonorSlot> availableSlots = donorSlotService.getAvailableSlots(id);

        return ResponseEntity.ok(availableSlots);
    }

}
