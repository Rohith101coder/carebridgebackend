package com.carebridge.backend.visitbookingManagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.visitbookingManagement.dto.SlotRequest;
import com.carebridge.backend.visitbookingManagement.dto.SlotResponse;
import com.carebridge.backend.visitbookingManagement.service.SlotService;

// import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orphanage/slot")
@RequiredArgsConstructor
public class SlotController {

    private final SlotService slotService;

  

    @PostMapping("/create")
    public ResponseEntity<SlotResponse> createSlot(

            
            @RequestBody
            SlotRequest request
    ){

        SlotResponse response =
                slotService.createSlot(request);

        return ResponseEntity.ok(response);
    }
}