package com.carebridge.backend.visitbookingManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carebridge.backend.visitbookingManagement.entity.VisitBooking;
import com.carebridge.backend.visitbookingManagement.enums.VisitBookingStatus;

import java.util.List;
import java.util.Optional;


public interface VisitBookingRepo extends JpaRepository<VisitBooking, Long>{


    List<VisitBooking> findByOrphanageCareBridgeIdAndBookingStatus(String orphanageCareBridgeId, VisitBookingStatus visitBookingStatus);

    Optional<VisitBooking> findByBookingIdAndOrphanageCareBridgeId(String bookId, String orpId);
}
