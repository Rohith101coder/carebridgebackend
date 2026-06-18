package com.carebridge.backend.visitbookingManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;

import com.carebridge.backend.visitbookingManagement.entity.VisitBooking;
import com.carebridge.backend.visitbookingManagement.enums.VisitBookingStatus;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface VisitBookingRepo extends JpaRepository<VisitBooking, Long>{


    List<VisitBooking> findByOrphanageCareBridgeIdAndBookingStatus(String orphanageCareBridgeId, VisitBookingStatus visitBookingStatus);

    Optional<VisitBooking> findByBookingIdAndOrphanageCareBridgeId(String bookId, String orpId);

    int countByDonorCareBridgeIdAndBookingStatus(
        String id,
        VisitBookingStatus status
);

List<VisitBooking>
findByDonorCareBridgeIdAndBookingStatusInOrderByCreatedAtAsc(
        String donorId,
        List<VisitBookingStatus> statuses,
        Pageable pageable);


        long countByOrphanageCareBridgeId(String orphanageCareBridgeId);

        List<VisitBooking> findTop5ByOrphanageCareBridgeIdOrderByCreatedAtDesc(
        String orphanageCareBridgeId
);

List<VisitBooking> findByOrphanageCareBridgeId(String orphanageCareBridgeId);
}
