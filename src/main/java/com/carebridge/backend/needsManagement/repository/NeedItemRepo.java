package com.carebridge.backend.needsManagement.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Lock;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.carebridge.backend.needsManagement.entity.NeedItem;
import com.carebridge.backend.needsManagement.enums.CategoryType;

// import jakarta.persistence.LockModeType;

import java.util.List;
// import java.util.List;
import java.util.Optional;


public interface NeedItemRepo extends JpaRepository<NeedItem, Long>{
    
    boolean existsByNameIgnoreCaseAndCategoryAndOrphanageCareBridgeId(
        String name, CategoryType category, String orphanageId
    );

    // Optional<NeedItem> findByNeedItemId(String needItemId);

//     @Lock(LockModeType.PESSIMISTIC_WRITE)

// @Query("""
//     SELECT n
//     FROM NeedItem n
//     WHERE n.needItemId = :needItemId
// """)
// Optional<NeedItem> findByNeedItemIdForUpdate(
//         @Param("needItemId")
//         String needItemId
// );

    void deleteByNeedItemId(String id);

    List<NeedItem> getByOrphanageCareBridgeId(String id);

    Optional<NeedItem>  findByNeedItemId(String needItemId);

@Query("""
    SELECT n
    FROM NeedItem n
    WHERE (n.fulfilledQuantity + n.reservedQuantity) < n.quantity
    AND n.priority IN (
        com.carebridge.backend.needsManagement.enums.PriorityLevel.HIGH,
        com.carebridge.backend.needsManagement.enums.PriorityLevel.MEDIUM
    )
""")
List<NeedItem> getUrgentNeeds(Pageable pageable);

   @Query("""
    SELECT n.name
    FROM NeedItem n
    WHERE n.needItemId = :id
""")
String getItemName(@Param("id") String id);
}
