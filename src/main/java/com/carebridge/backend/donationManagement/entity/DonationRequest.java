package com.carebridge.backend.donationManagement.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.carebridge.backend.donationManagement.enums.DonationStatus;
import com.carebridge.backend.donationManagement.enums.DonationType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "donation_request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String donationRequestId;

    @Column(nullable = false)
    private String donorCareBridgeId;

    @Column(nullable = false)
    private String orphanageCareBridgeId;

    @Column(nullable = false)
    private String needItemId;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DonationType donationType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DonationStatus donationStatus;

    // for IN_PERSON

    private LocalDateTime expectedVisitDateTime;

    // for ONLINE_ORDER

    private LocalDate expectedDeliveryDate;

    private String orderProofImageUrl;

    private String platformName;

    private String trackingId;

    private String message;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate(){

        this.createdAt = LocalDateTime.now();

        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){

        this.updatedAt = LocalDateTime.now();
    }
}