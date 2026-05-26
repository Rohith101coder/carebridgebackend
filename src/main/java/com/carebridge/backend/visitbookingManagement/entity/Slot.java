package com.carebridge.backend.visitbookingManagement.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.carebridge.backend.visitbookingManagement.enums.SlotStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String slotId;

    

    @Column(nullable = false)
    private String orphanageCareBridgeId;

   

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

   
    @Column(nullable = false)
    private Integer maxVisitors;

    @Column(nullable = false)
    private Integer bookedCount = 0;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SlotStatus slotStatus;


    private String title;

    private String description;

  
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate(){

        this.createdAt = LocalDateTime.now();

        this.updatedAt = LocalDateTime.now();

        if(this.slotStatus == null){

            this.slotStatus = SlotStatus.AVAILABLE;
        }
    }

    @PreUpdate
    public void onUpdate(){

        this.updatedAt = LocalDateTime.now();
    }

}