package com.carebridge.backend.orphanageManagement.entity;

import java.time.LocalDateTime;

import com.carebridge.backend.authManagement.entity.User;
import com.carebridge.backend.common.enums.VerificationStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rejected_orphanage_profile")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RejectedOrpProfile {
    
     
    
    private Long id;

    @Column(name="carebridge_id", nullable=false, unique = true)
    private String carebridgeId;

    @Column(nullable = false)
    private String orphanageName;

    @Column(nullable = false)
    private String adminName;

    private String village;

    private String mandal;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private Integer numberOfChildren;

    @Column(nullable = false, unique = true)
    private String orphanageEmail;

    private boolean orphanageEmailVerified;

    @Column(nullable = false, unique = true)
    private String orphanagePhone;

    private String websiteLink;

    private String socialMediaLinks;

    @Column(nullable=false, unique = true)
    private String darpanId;

    @Column(nullable = false, unique = true)
    private String panNumber;

    
    @Column(nullable = false)
    private String panPhoto;

    @Column(nullable = false)
    private String jjActCertificatePhoto;

    private String orphanageProfilePic;

    
    private String adminProfilePic;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus verificationStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    // @PrePersist
    // public void onCreate(){
    //     this.createdAt = LocalDateTime.now();
    //     this.updateAt = LocalDateTime.now();
    // }

    // @PreUpdate
    // public void onUpdate(){
    //     this.updateAt = LocalDateTime.now();
    // }

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private User user;

}
