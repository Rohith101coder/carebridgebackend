package com.carebridge.backend.visitbookingManagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.visitbookingManagement.dto.DonorSlot;
import com.carebridge.backend.visitbookingManagement.dto.VisitBookingRequest;
import com.carebridge.backend.visitbookingManagement.dto.VisitBookingResponse;
import com.carebridge.backend.visitbookingManagement.service.DonorSlotService;

// import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/donor/slots")
@RequiredArgsConstructor
public class DonorSlotController {
    
    private final DonorSlotService donorSlotService;
      


    @GetMapping("/available-slots/{id}")
    public ResponseEntity<List<DonorSlot>> getAvailableSlots(@PathVariable String id){

        List<DonorSlot> availableSlots = donorSlotService.getAvailableSlots(id);

        return ResponseEntity.ok(availableSlots);
    }

       @PostMapping("/book")
    public ResponseEntity<VisitBookingResponse> bookVisit(

         
            @RequestBody
            VisitBookingRequest request
    ){

        VisitBookingResponse response =
                donorSlotService
                        .createVisitBooking(request);

        return ResponseEntity.ok(response);
    }



}
