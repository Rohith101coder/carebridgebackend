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
import com.carebridge.backend.visitbookingManagement.dto.VisitBookingDTO;
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

    @GetMapping("/pending-bookings")
    public ResponseEntity<List<VisitBookingDTO>> getPendings(){

        List<VisitBookingDTO> res= donorSlotService.getMyPendingBookings();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/confirmed-bookings")
    public ResponseEntity<List<VisitBookingDTO>> getconfirmed(){

        List<VisitBookingDTO> res= donorSlotService.getMyConfirmedBookings();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/cancelled-bookings")
    public ResponseEntity<List<VisitBookingDTO>> getCancelled(){

        List<VisitBookingDTO> res= donorSlotService.getMyCancelledBookings();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/rejected-bookings")
    public ResponseEntity<List<VisitBookingDTO>> getrejected(){

        List<VisitBookingDTO> res= donorSlotService.getMyRejectedBookings();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/notvisited-bookings")
    public ResponseEntity<List<VisitBookingDTO>> getNotvisited(){

        List<VisitBookingDTO> res= donorSlotService.getMyNotVisitedBookings();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/completed-bookings")
    public ResponseEntity<List<VisitBookingDTO>> getCompletes(){

        List<VisitBookingDTO> res= donorSlotService.getMyCompletedBookings();
        return ResponseEntity.ok(res);
    }
    



}
