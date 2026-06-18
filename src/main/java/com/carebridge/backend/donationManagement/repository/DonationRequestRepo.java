package com.carebridge.backend.donationManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.carebridge.backend.donationManagement.entity.DonationRequest;
import com.carebridge.backend.donationManagement.enums.DonationStatus;

import java.util.List;
import java.util.Optional;


public interface DonationRequestRepo extends JpaRepository<DonationRequest, Long> {
    
    List<DonationRequest> findByDonorCareBridgeIdAndDonationStatus(String donorCareBridgeId, DonationStatus donationStatus);

    Optional<DonationRequest> findByDonationRequestIdAndDonorCareBridgeId(String donationId, String donorId);

    Optional<DonationRequest>  findByDonationRequestId(String donationRequestId);

    List<DonationRequest> findByOrphanageCareBridgeIdAndDonationStatus(String id, DonationStatus status);

    DonationRequest findByDonationRequestIdAndOrphanageCareBridgeId(String donationId, String orpId);

    long countByDonationStatus(DonationStatus status);

    int countByDonorCareBridgeIdAndDonationStatus(String donorCareBridgeId, DonationStatus donationStatus);

   @Query("""
    SELECT COUNT(DISTINCT d.orphanageCareBridgeId)
    FROM DonationRequest d
    WHERE d.donorCareBridgeId = :id
""")
int getOrps(@Param("id") String id);


    // @Query("SELECT d.orphanageCareBridgeId FROM DonationRequest d WHERE d.donorCareBridgeId = :donorId")
    // List<String> getOrpNames(String donorId);

    @Query("""
    SELECT DISTINCT d.orphanageCareBridgeId
    FROM DonationRequest d
    WHERE d.donorCareBridgeId = :donorId
""")
List<String> getOrpNames(
        @Param("donorId") String donorId);



   @Query("""
    SELECT d.needItemId
    FROM DonationRequest d
    WHERE d.orphanageCareBridgeId = :orphanageId
      AND d.donationStatus = :status
""")
List<String> findNeedItemIdsByOrphanageAndStatus(
        @Param("orphanageId") String orphanageId,
        @Param("status") DonationStatus status
);
}
