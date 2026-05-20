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
    
    @Id
    private Long id;

    @Column(name = "donor_name", nullable = false)
    private String name;

    @Column(name = "donor_DOB", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "donor_designation", nullable = false)
    private String designation;

    private String houseNum;

    @Column(name = "carebridgeID", nullable = false)
    private String careBridgeID;

    @Column(name = "village")
    private String village;

    private String mandal;

    private String district;

    private String state;

    private String country;

  @Column(nullable = false)
    private String phone;

   
    private String profilePic;

    
    private String panNumber;
    

    private String panPhoto;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus donorStatus;

    private DonorSubscriptionStatus subscriptionStatus;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",
        referencedColumnName = "id",
        nullable = false
    )
    private User user;

}
