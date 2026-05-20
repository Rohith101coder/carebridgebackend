package com.carebridge.backend.donorManagement.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.carebridge.backend.authManagement.entity.User;
import com.carebridge.backend.common.enums.DonorSubscriptionStatus;
import com.carebridge.backend.common.enums.VerificationStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rejected_donor_profile")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RejectedDonorProfile {
    
    private Long id;

    @Column(name = "donor_name", nullable = false)
    private String name;

    @Column(name = "donor_DOB", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "donor_designation", nullable = false)
    private String designation;

    private String houseNum;

    @Column(name = "carebridgeID", nullable = false, unique = true)
    private String careBridgeID;

    @Column(name = "village")
    private String village;

    private String mandal;

    private String district;

    private String state;

    private String country;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phone;

   
    private String profilePic;

    @Column(name = "pan_number", nullable = false, unique = true)
    private String panNumber;
    
    @Column(nullable = false)
    private String panPhoto;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus donorStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DonorSubscriptionStatus subscriptionStatus;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",
        referencedColumnName = "id",
        nullable = false,
        unique = true
    )
    private User user;

}
