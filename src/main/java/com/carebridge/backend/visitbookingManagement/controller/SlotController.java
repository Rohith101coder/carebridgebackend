package com.carebridge.backend.visitbookingManagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carebridge.backend.visitbookingManagement.dto.BookingRejectionRequest;
import com.carebridge.backend.visitbookingManagement.dto.SlotRequest;
import com.carebridge.backend.visitbookingManagement.dto.SlotResponse;
import com.carebridge.backend.visitbookingManagement.dto.VisitBookingResponse;
import com.carebridge.backend.visitbookingManagement.entity.Slot;
import com.carebridge.backend.visitbookingManagement.entity.VisitBooking;
import com.carebridge.backend.visitbookingManagement.service.OrpSlotService;
import com.carebridge.backend.visitbookingManagement.service.SlotService;

// import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orphanage/slot")
@RequiredArgsConstructor
public class SlotController {

    private final SlotService slotService;
    private final OrpSlotService orpSlotService;

    @PostMapping("/create")
    public ResponseEntity<SlotResponse> createSlot(@RequestBody SlotRequest request){
        SlotResponse response =
                slotService.createSlot(request);
        return ResponseEntity.ok(response);
    }

      @GetMapping("/allSlots")
    public ResponseEntity<List<Slot>> getOrpSlots(){
        List<Slot> futureSlots = orpSlotService.getOrpSlots();
        return ResponseEntity.ok(futureSlots);
    }

    @DeleteMapping("/deleteSlot/{id}")
    public ResponseEntity<SlotResponse> deleteSlot(@PathVariable String id){
        SlotResponse response = orpSlotService.deleteSlot(id);
        return ResponseEntity.ok(response);
    }


     @GetMapping("/bookings")
    public ResponseEntity<List<VisitBooking>> getPendingBookings(){

        List<VisitBooking> response =
                orpSlotService.getPendingBookings();

        return ResponseEntity.ok(response);
    }

  

     @PatchMapping("/confirm/{bookingId}")
    public ResponseEntity<VisitBookingResponse> confirmBooking(

            @PathVariable
            String bookingId
    ){

        VisitBookingResponse response =
                orpSlotService.confirmBooking(
                        bookingId
                );

        return ResponseEntity.ok(response);
    }

       @PatchMapping("/reject/{bookingId}")
    public ResponseEntity<VisitBookingResponse> rejectBooking(

            @PathVariable
            String bookingId,

            @RequestBody
            BookingRejectionRequest request
    ){

        VisitBookingResponse response =
                orpSlotService.rejectBooking(
                        bookingId,
                        request
                );

        return ResponseEntity.ok(response);
    }


@GetMapping("/approvals")
    public ResponseEntity<List<VisitBooking>> getAllApprovedVisits(){

        List<VisitBooking> approvals = orpSlotService.getAllApprovedSlots();

        return ResponseEntity.ok(approvals);
    }

    @PatchMapping("/completed/{id}")
    public ResponseEntity<SlotResponse> markAsCompleted(@PathVariable String id){
        SlotResponse response = orpSlotService.markAsCompleted(id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/notVisited/{id}")
    public ResponseEntity<SlotResponse> markAsNotVisited(@PathVariable String id){
        SlotResponse response = orpSlotService.markAsNotVisited(id);
        return ResponseEntity.ok(response);
    }

}