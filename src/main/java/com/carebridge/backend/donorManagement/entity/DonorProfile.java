package com.carebridge.backend.donorManagement.entity;


import java.time.LocalDate;
import java.time.LocalDateTime;

import com.carebridge.backend.authManagement.entity.User;
import com.carebridge.backend.common.enums.DonorSubscriptionStatus;
import com.carebridge.backend.common.enums.VerificationStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "donor_profile")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DonorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",
        referencedColumnName = "id",
        nullable = false,
        unique = true
    )
    private User user;

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
