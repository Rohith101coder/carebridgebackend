package com.carebridge.backend.orphanageManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.carebridge.backend.orphanageManagement.entity.OrphanageProfile;
// import java.util.List;
import java.util.Optional;

import com.carebridge.backend.authManagement.entity.User;
import java.util.List;
import com.carebridge.backend.common.enums.VerificationStatus;



public interface OrphanageProfileRepository extends JpaRepository<OrphanageProfile, Long>{
    Optional<OrphanageProfile>  findByUser(User user);
    OrphanageProfile findByOrphanageEmail(String orphanageEmail);

    List<OrphanageProfile> findByVerificationStatus(VerificationStatus verificationStatus);

    Optional<OrphanageProfile>  findByCarebridgeId(String carebridgeId);

    void deleteByCarebridgeId(String id);

    // Optional<OrphanageProfile> findByCareBridgeId(String id);

    long countByVerificationStatus(VerificationStatus status);

    @Query("SELECT COUNT(DISTINCT o.district) FROM OrphanageProfile o")
long countDistinctDistricts();

   @Query("""
    SELECT o.orphanageName
    FROM OrphanageProfile o
    WHERE o.carebridgeId = :id
""")
String getOrpName(@Param("id") String id);

   @Query("""
    SELECT o.numberOfChildren
    FROM OrphanageProfile o
    WHERE o.carebridgeId = :id
""")
int getChildrenCount(@Param("id") String id);
}
