package com.carebridge.backend.visitbookingManagement.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.carebridge.backend.authManagement.entity.User;
import com.carebridge.backend.authManagement.exception.UserNotFoundException;
import com.carebridge.backend.authManagement.repository.UserRepository;
import com.carebridge.backend.donorManagement.entity.DonorProfile;
import com.carebridge.backend.donorManagement.exception.DonorProfileNotFoundException;
import com.carebridge.backend.donorManagement.repository.DonorProfileRepository;
import com.carebridge.backend.needsManagement.exception.CommonException;
import com.carebridge.backend.notificationManagement.service.EmailService;
import com.carebridge.backend.orphanageManagement.entity.OrphanageProfile;
import com.carebridge.backend.orphanageManagement.exception.OrphanageProfileNotFoundException;
import com.carebridge.backend.orphanageManagement.repository.OrphanageProfileRepository;
import com.carebridge.backend.visitbookingManagement.dto.BookingRejectionRequest;
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
public class OrpSlotService {

    private final UserRepository userRepository;

    private final OrphanageProfileRepository orphanageProfileRepository;

    private final VisitBookingRepo visitBookingRepo;

    private final SlotRepo slotRepo;

    private final DonorProfileRepository donorProfileRepository;

    private final EmailService emailService;

    // =====================================================
    // ✅ GET ALL PENDING BOOKINGS
    // =====================================================

    public List<VisitBooking> getPendingBookings(){

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

        OrphanageProfile orphanage =
                orphanageProfileRepository
                        .findByUser(user)
                        .orElseThrow(() ->
                                new OrphanageProfileNotFoundException(
                                        "Orphanage not found"
                                ));

        return visitBookingRepo
                .findByOrphanageCareBridgeIdAndBookingStatus(
                        orphanage.getCarebridgeId(),
                        VisitBookingStatus.PENDING
                );
    }

    // =====================================================
    // ✅ CONFIRM BOOKING
    // =====================================================

    @Transactional
    public VisitBookingResponse confirmBooking(
            String bookingId
    ){

        // ✅ authenticated orphanage

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

        OrphanageProfile orphanage =
                orphanageProfileRepository
                        .findByUser(user)
                        .orElseThrow(() ->
                                new OrphanageProfileNotFoundException(
                                        "Orphanage not found"
                                ));

        // ✅ fetch booking

        VisitBooking booking =
                visitBookingRepo
                        .findByBookingIdAndOrphanageCareBridgeId(
                                bookingId,
                                orphanage.getCarebridgeId()
                        )
                        .orElseThrow(() ->
                                new CommonException(
                                        "Booking not found"
                                ));

        // ✅ only pending bookings

        if(booking.getBookingStatus()
                != VisitBookingStatus.PENDING){

            throw new CommonException(
                    "Booking already processed"
            );
        }

        // ✅ confirm booking

        booking.setBookingStatus(
                VisitBookingStatus.CONFIRMED
        );

        visitBookingRepo.save(booking);

        // ✅ donor details

        DonorProfile donor =
                donorProfileRepository
                        .findByCareBridgeID(
                                booking.getDonorCareBridgeId()
                        )
                        .orElseThrow(() ->
                                new DonorProfileNotFoundException(
                                        "Donor not found"
                                ));

        // ✅ slot details

        Slot slot =
                slotRepo
                        .findBySlotId(
                                booking.getSlotId()
                        )
                        .orElseThrow(() ->
                                new CommonException(
                                        "Slot not found"
                                ));

        // ✅ send mail

        emailService.bookingConfirmationMail(
                donor.getUser().getEmail(),
                donor.getName(),
                booking.getBookingId(),
                slot.getDate(),
                slot.getStartTime(),
                slot.getEndTime(),
                booking.getNumberOfVisitors(),
                booking.getBookingStatus(),
                orphanage.getOrphanageName(),
                orphanage.getDistrict(),
                orphanage.getState(),
                orphanage.getWebsiteLink(),
                orphanage.getSocialMediaLinks()
        );

        return new VisitBookingResponse(
                "Booking confirmed successfully",
                bookingId
        );
    }

    // =====================================================
    // ✅ REJECT BOOKING
    // =====================================================

    @Transactional
    public VisitBookingResponse rejectBooking(
            String bookingId, BookingRejectionRequest request
    ){

        // ✅ authenticated orphanage

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

        OrphanageProfile orphanage =
                orphanageProfileRepository
                        .findByUser(user)
                        .orElseThrow(() ->
                                new OrphanageProfileNotFoundException(
                                        "Orphanage not found"
                                ));

        // ✅ fetch booking

        VisitBooking booking =
                visitBookingRepo
                        .findByBookingIdAndOrphanageCareBridgeId(
                                bookingId,
                                orphanage.getCarebridgeId()
                        )
                        .orElseThrow(() ->
                                new CommonException(
                                        "Booking not found"
                                ));

        // ✅ only pending bookings

        if(booking.getBookingStatus()
                != VisitBookingStatus.PENDING){

            throw new CommonException(
                    "Booking already processed"
            );
        }

        // ✅ fetch slot with lock

        Slot slot =
                slotRepo
                        .findBySlotId(
                                booking.getSlotId()
                        )
                        .orElseThrow(() ->
                                new CommonException(
                                        "Slot not found"
                                ));

        // ✅ reset seats

        slot.setBookedCount(

                slot.getBookedCount()
                -
                booking.getNumberOfVisitors()
        );

        // ✅ slot available again

        if(slot.getBookedCount()
                < slot.getMaxVisitors()){

            slot.setSlotStatus(
                    SlotStatus.AVAILABLE
            );
        }

        // ✅ reject booking

        booking.setBookingStatus(
                VisitBookingStatus.REJECTED
        );

        // ✅ save

        slotRepo.save(slot);

        visitBookingRepo.save(booking);

        // ✅ donor details

        DonorProfile donor =
                donorProfileRepository
                        .findByCareBridgeID(
                                booking.getDonorCareBridgeId()
                        )
                        .orElseThrow(() ->
                                new DonorProfileNotFoundException(
                                        "Donor not found"
                                ));

        // ✅ send rejection mail

        emailService.bookingRejectedMail(
                donor.getUser().getEmail(),
                donor.getName(),
                booking.getBookingId(),
                  request.getReason()
        );

        return new VisitBookingResponse(
                "Booking rejected successfully",
                bookingId
        );
    }
}
