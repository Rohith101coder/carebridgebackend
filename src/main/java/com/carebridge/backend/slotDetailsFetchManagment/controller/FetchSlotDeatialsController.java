package com.carebridge.backend.slotDetailsFetchManagment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.slotDetailsFetchManagment.dto.OrpAndSlotsDetailsDTO;
import com.carebridge.backend.slotDetailsFetchManagment.service.FetchSlotsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FetchSlotDeatialsController {
    
    private final FetchSlotsService fetchSlotsService;


    @GetMapping("/allSlots")
    public ResponseEntity<List<OrpAndSlotsDetailsDTO>> getDetails(){
        List<OrpAndSlotsDetailsDTO> data = fetchSlotsService.getDetails();
        return ResponseEntity.ok(data);
    }
}
