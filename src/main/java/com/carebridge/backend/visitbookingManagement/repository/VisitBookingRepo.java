package com.carebridge.backend.visitbookingManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.carebridge.backend.visitbookingManagement.entity.VisitBooking;
import com.carebridge.backend.visitbookingManagement.enums.VisitBookingStatus;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface VisitBookingRepo extends JpaRepository<VisitBooking, Long>{


    List<VisitBooking> findByOrphanageCareBridgeIdAndBookingStatus(String orphanageCareBridgeId, VisitBookingStatus visitBookingStatus);

    Optional<VisitBooking> findByBookingIdAndOrphanageCareBridgeId(String bookId, String orpId);

    int countByDonorCareBridgeIdAndVisitBookingStatus(String id,VisitBookingStatus status);

 @Query("""
    SELECT v
    FROM VisitBooking v
    WHERE v.donorCareBridgeId = :id
    AND (
        v.bookingStatus = com.carebridge.backend.visitbookingManagement.enums.VisitBookingStatus.PENDING
        OR
        v.bookingStatus = com.carebridge.backend.visitbookingManagement.enums.VisitBookingStatus.CONFIRMED
    )
    ORDER BY v.visitDate ASC
""")
List<VisitBooking> getDonorUpcomingBookings(
        @Param("id") String id,
        Pageable pageable);
}
