package com.carebridge.backend.visitbookingManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carebridge.backend.visitbookingManagement.entity.VisitBooking;

public interface VisitBookingRepo extends JpaRepository<VisitBooking, Long>{

}
