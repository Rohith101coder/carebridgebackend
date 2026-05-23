package com.carebridge.backend.needsManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carebridge.backend.needsManagement.entity.NeedItem;
import com.carebridge.backend.needsManagement.enums.CategoryType;

public interface NeedItemRepo extends JpaRepository<NeedItem, Long>{
    
    boolean existsByNameIgnoreCaseAndCategoryAndOrphanageCareBridgeId(
        String name, CategoryType category, String orphanageId
    );
}
