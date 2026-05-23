package com.carebridge.backend.needsManagement.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.carebridge.backend.needsManagement.enums.CategoryType;
import com.carebridge.backend.needsManagement.enums.PriorityLevel;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "need_item")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NeedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryType category;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer fulfilledQuantity = 0;

    @Column(nullable = false)
    private BigDecimal pricePerQuantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PriorityLevel priority;

    @Column(nullable = false)
    private String orphanageCareBridgeId;

    @ElementCollection
    private List<String> productLinks;

    @Column(nullable = false, unique = true)
    private String needItemId;

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
