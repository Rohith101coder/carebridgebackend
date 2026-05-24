package com.carebridge.backend.needsManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carebridge.backend.needsManagement.entity.NeedItem;
import com.carebridge.backend.needsManagement.enums.CategoryType;

import java.util.List;
// import java.util.List;
import java.util.Optional;


public interface NeedItemRepo extends JpaRepository<NeedItem, Long>{
    
    boolean existsByNameIgnoreCaseAndCategoryAndOrphanageCareBridgeId(
        String name, CategoryType category, String orphanageId
    );

    Optional<NeedItem> findByNeedItemId(String needItemId);

    void deleteByNeedItemId(String id);

    List<NeedItem> getgetByOrphanageCareBridgeId(String id);
}
