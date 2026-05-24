package com.carebridge.backend.needsManagement.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.carebridge.backend.needsManagement.enums.CategoryType;
import com.carebridge.backend.needsManagement.enums.PriorityLevel;

// import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeletedItems {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    private String name;

    private String description;

    
    @Enumerated(EnumType.STRING)
    private CategoryType category;

    
    private Integer quantity;

    
    private Integer fulfilledQuantity ;

    
    private BigDecimal pricePerQuantity;

    @Enumerated(EnumType.STRING)
    private PriorityLevel priority;

    
    private String orphanageCareBridgeId;

    @ElementCollection
    private List<String> productLinks;

    private String needItemId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
