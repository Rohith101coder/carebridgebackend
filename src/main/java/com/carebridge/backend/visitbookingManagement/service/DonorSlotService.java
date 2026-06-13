package com.carebridge.backend.visitbookingManagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
// import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.carebridge.backend.authManagement.entity.User;
import com.carebridge.backend.authManagement.exception.UserNotFoundException;
import com.carebridge.backend.authManagement.repository.UserRepository;
// import com.carebridge.backend.donationManagement.enums.DonationStatus;
import com.carebridge.backend.donorManagement.entity.DonorProfile;
import com.carebridge.backend.donorManagement.exception.DonorProfileNotFoundException;
import com.carebridge.backend.donorManagement.repository.DonorProfileRepository;
import com.carebridge.backend.needsManagement.exception.CommonException;
import com.carebridge.backend.notificationManagement.service.EmailService;
import com.carebridge.backend.orphanageManagement.entity.OrphanageProfile;
import com.carebridge.backend.orphanageManagement.repository.OrphanageProfileRepository;
import com.carebridge.backend.visitbookingManagement.dto.DonorSlot;
import com.carebridge.backend.visitbookingManagement.dto.VisitBookingRequest;
import com.carebridge.backend.visitbookingManagement.dto.VisitBookingResponse;
import com.carebridge.backend.visitbookingManagement.entity.Slot;
import com.carebridge.backend.visitbookingManagement.entity.VisitBooking;
import com.carebridge.backend.visitbookingManagement.enums.SlotStatus;
import com.carebridge.backend.visitbookingManagement.enums.VisitBookingStatus;
import com.carebridge.backend.visitbookingManagement.repository.SlotRepo;
import com.carebridge.backend.visitbookingManagement.repository.VisitBookingRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DonorSlotService {
    

    private final SlotRepo slotRepo;
    private final UserRepository userRepository;
    private final DonorProfileRepository donorProfileRepository;
    private final OrphanageProfileRepository orphanageProfileRepository;
    private final VisitBookingRepo visitBookingRepo;
    private final EmailService emailService;

    public List<DonorSlot> getAvailableSlots(String orpId){
        
        List<Slot> availableSlots = slotRepo.findByOrphanageCareBridgeIdAndSlotStatus(orpId, SlotStatus.AVAILABLE);
      availableSlots.sort(

        Comparator.comparing(Slot::getDate)

                .thenComparing(Slot::getStartTime)
);


        List<DonorSlot> donorAvailableSlots = new ArrayList<>();

        for(Slot slot : availableSlots){
            DonorSlot dSlot = new DonorSlot();
            dSlot.setSlotId(slot.getSlotId());
            dSlot.setDate(slot.getDate());
            dSlot.setStarTime(slot.getStartTime());
            dSlot.setEndTime(slot.getEndTime());
            dSlot.setMaxVisitors(slot.getMaxVisitors());
            dSlot.setSlotStatus(slot.getSlotStatus());
            donorAvailableSlots.add(dSlot);
        }

        return donorAvailableSlots;
    }






@Transactional
public VisitBookingResponse createVisitBooking(
    VisitBookingRequest request
){


     Authentication authentication =
            SecurityContextHolder
                    .getContext()
                    .getAuthentication();

    String email = authentication.getName();

    User user =
            userRepository
                    .findByEmail(email)
                    .orElseThrow(() ->
                            new UserNotFoundException(
                                    "User not found"
                            ));

    DonorProfile donor =
            donorProfileRepository
                    .findByUser(user)
                    .orElseThrow(() ->
                            new DonorProfileNotFoundException(
                                    "Donor not found"
                            ));
            Slot slot =
            slotRepo
                    .findBySlotId(
                            request.getSlotId()
                    )
                    .orElseThrow(() ->
                            new CommonException(
                                    "Slot not found"
                            ));                   

            
            OrphanageProfile orpProfile = orphanageProfileRepository.findByCarebridgeId(slot.getOrphanageCareBridgeId()).orElseThrow(()-> new CommonException("orp profile not found"));

              LocalDateTime slotEndDateTime =
            LocalDateTime.of(
                    slot.getDate(),
                    slot.getEndTime()
            );

    if(slotEndDateTime.isBefore(
            LocalDateTime.now()
    )){

        slot.setSlotStatus(
                SlotStatus.EXPIRED
        );

        slotRepo.save(slot);

        throw new CommonException(
                "Slot already expired"
        );
    }


      int availableCapacity =
            slot.getMaxVisitors()
            -
            slot.getBookedCount();

    if(request.getNumberOfVisitors()
            > availableCapacity){

        throw new CommonException(
                "Requested visitors exceed available capacity"
        );
    }


    VisitBooking booking =
            new VisitBooking();

    String bookingId =
            "CB-VB-"
            +
            UUID.randomUUID()
                    .toString()
                    .substring(0, 8)
                    .toUpperCase();

    booking.setBookingId(
            bookingId
    );

    booking.setSlotId(
            slot.getSlotId()
    );

    booking.setDonorCareBridgeId(
            donor.getCareBridgeID()
    );

    booking.setOrphanageCareBridgeId(
            slot.getOrphanageCareBridgeId()
    );

    booking.setNumberOfVisitors(
            request.getNumberOfVisitors()
    );

    booking.setMessage(
            request.getMessage()
    );

    booking.setBookingStatus(
            VisitBookingStatus.PENDING
    );


     slot.setBookedCount(

            slot.getBookedCount()
            +
            request.getNumberOfVisitors()
    );

    System.out.println("-------------------------");
    System.out.println(slot.getBookedCount());
    System.out.println("-----------------------------");



         if(slot.getBookedCount()
            .equals(slot.getMaxVisitors())){

        slot.setSlotStatus(
                SlotStatus.FULL
        );
    }

      visitBookingRepo.save(booking);

    slotRepo.save(slot);

    
       availableCapacity =
            slot.getMaxVisitors()
            -
            slot.getBookedCount();

    emailService.bookingNotification(orpProfile.getOrphanageEmail(),bookingId,slot.getSlotId(),request.getNumberOfVisitors(),availableCapacity,donor.getCareBridgeID(),donor.getName(),donor.getPhone(),request.getMessage(),slot.getDate(),slot.getStartTime(),slot.getEndTime());


    return new VisitBookingResponse("Visit booking created successfully", bookingId);
}



}
