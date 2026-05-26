package com.carebridge.backend.visitbookingManagement.entity;

import java.time.LocalDateTime;

import com.carebridge.backend.visitbookingManagement.enums.VisitBookingStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "visit_booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisitBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String bookingId;


    @Column(nullable = false)
    private String slotId;


    @Column(nullable = false)
    private String donorCareBridgeId;

    @Column(nullable = false)
    private String orphanageCareBridgeId;


    @Column(nullable = false)
    private Integer numberOfVisitors;

    private String message;

  

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VisitBookingStatus bookingStatus;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate(){

        this.createdAt = LocalDateTime.now();

        this.updatedAt = LocalDateTime.now();

        if(this.bookingStatus == null){

            this.bookingStatus =
                    VisitBookingStatus.PENDING;
        }
    }

    @PreUpdate
    public void onUpdate(){

        this.updatedAt = LocalDateTime.now();
    }
}
