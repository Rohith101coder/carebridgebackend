package com.carebridge.backend.dashBoardManagement.orphanagedashboardManagement.service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.carebridge.backend.authManagement.entity.User;
import com.carebridge.backend.authManagement.repository.UserRepository;
import com.carebridge.backend.common.enums.VerificationStatus;
import com.carebridge.backend.dashBoardManagement.orphanagedashboardManagement.dto.ActivityDTO;
import com.carebridge.backend.dashBoardManagement.orphanagedashboardManagement.dto.DashboardResponse;
import com.carebridge.backend.dashBoardManagement.orphanagedashboardManagement.dto.DonationSummaryDTO;
import com.carebridge.backend.dashBoardManagement.orphanagedashboardManagement.dto.NeedSummaryDTO;
import com.carebridge.backend.dashBoardManagement.orphanagedashboardManagement.dto.OverviewDTO;
import com.carebridge.backend.dashBoardManagement.orphanagedashboardManagement.dto.VisitSummaryDTO;
import com.carebridge.backend.dashBoardManagement.orphanagedashboardManagement.enums.ActivityType;
import com.carebridge.backend.donationManagement.repository.ItemDeliveredRepo;
import com.carebridge.backend.donorManagement.entity.DonorProfile;
import com.carebridge.backend.donorManagement.repository.DonorProfileRepository;
import com.carebridge.backend.needsManagement.entity.NeedItem;
import com.carebridge.backend.needsManagement.exception.CommonException;
import com.carebridge.backend.needsManagement.repository.NeedItemRepo;
import com.carebridge.backend.orphanageManagement.entity.OrphanageProfile;
import com.carebridge.backend.orphanageManagement.repository.OrphanageProfileRepository;
import com.carebridge.backend.visitbookingManagement.entity.Slot;
import com.carebridge.backend.visitbookingManagement.enums.VisitBookingStatus;
import com.carebridge.backend.visitbookingManagement.repository.SlotRepo;
import com.carebridge.backend.visitbookingManagement.repository.VisitBookingRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrphanageDashboardService {

    private final OrphanageProfileRepository orphanageProfileRepository;

private final NeedItemRepo needItemRepository;

private final VisitBookingRepo visitBookingRepository;

private final SlotRepo slotRepository;

private final DonorProfileRepository donorProfileRepository;

private final ItemDeliveredRepo itemDeliveredResponseRepository;

private final UserRepository userRepository;
    



    public DashboardResponse getDashboard() {

    Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new CommonException("User not found"));

    OrphanageProfile orphanageProfile =
            orphanageProfileRepository.findByUser(user)
                    .orElseThrow(() ->
                            new CommonException("Orphanage profile not found"));

        if (orphanageProfile.getVerificationStatus() != VerificationStatus.VERIFIED) {

    throw new CommonException(
            "Your orphanage profile is not verified yet."
    );
}
    String orphanageCareBridgeId =
            orphanageProfile.getCarebridgeId();

    OverviewDTO overview =
            buildOverview(orphanageCareBridgeId);

    List<NeedSummaryDTO> activeNeeds =
            buildActiveNeeds(orphanageCareBridgeId);

    List<VisitSummaryDTO> upcomingVisits =
            buildUpcomingVisits(orphanageCareBridgeId);

    List<DonationSummaryDTO> recentDonations =
            buildRecentDonations(orphanageCareBridgeId);

    List<ActivityDTO> recentActivities =
            buildRecentActivities(
                    orphanageCareBridgeId,
                    recentDonations
            );

    return DashboardResponse.builder()
            .overview(overview)
            .activeNeeds(activeNeeds)
            .upcomingVisits(upcomingVisits)
            .recentDonations(recentDonations)
            .recentActivities(recentActivities)
            .build();
}

private OverviewDTO buildOverview(String orphanageCareBridgeId) {

    OrphanageProfile orphanage =
            orphanageProfileRepository
                    .findByCarebridgeId(orphanageCareBridgeId)
                    .orElseThrow(() ->
                            new CommonException("Orphanage not found"));

    List<NeedItem> needs =
            needItemRepository
                    .findByOrphanageCareBridgeId(orphanageCareBridgeId);

    int activeNeedsCount = (int) needs.stream()
            .filter(n ->
                    n.getQuantity() > n.getFulfilledQuantity())
            .count();

    double totalDonationAmount =
            needs.stream()
                    .map(n ->
                            n.getPricePerQuantity()
                                    .multiply(
                                            BigDecimal.valueOf(
                                                    n.getFulfilledQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .doubleValue();

    int upcomingVisitsCount =
            (int) visitBookingRepository
                    .findByOrphanageCareBridgeId(orphanageCareBridgeId)
                    .stream()
                    .filter(v ->
                            v.getBookingStatus() ==
                                    VisitBookingStatus.PENDING
                                    ||
                                    v.getBookingStatus() ==
                                            VisitBookingStatus.CONFIRMED)
                    .count();

    return OverviewDTO.builder()
            .childrenCount(orphanage.getNumberOfChildren())
            .activeNeedsCount(activeNeedsCount)
            .totalDonationsAmount(totalDonationAmount)
            .upcomingVisitsCount(upcomingVisitsCount)
            .build();
}

private List<NeedSummaryDTO> buildActiveNeeds(
        String orphanageCareBridgeId) {

    return needItemRepository
            .findByOrphanageCareBridgeId(orphanageCareBridgeId)
            .stream()
            .filter(n ->
                    n.getQuantity() > n.getFulfilledQuantity())
            .sorted(
                    Comparator.comparing(
                            NeedItem::getCreatedAt)
                            .reversed()
            )
            .limit(5)
            .map(n ->
                    NeedSummaryDTO.builder()
                            .needId(n.getId())
                            .itemName(n.getName())
                            .requiredQuantity(n.getQuantity())
                            .receivedQuantity(
                                    n.getFulfilledQuantity())
                            .remainingQuantity(
                                    n.getQuantity()
                                            -
                                            n.getFulfilledQuantity())
                            .priority(n.getPriority())
                            .build())
            .toList();
}


private List<VisitSummaryDTO> buildUpcomingVisits(
        String orphanageCareBridgeId) {

    return visitBookingRepository
            .findByOrphanageCareBridgeId(orphanageCareBridgeId)
            .stream()
            .filter(v ->
                    v.getBookingStatus() ==
                            VisitBookingStatus.PENDING
                            ||
                            v.getBookingStatus() ==
                                    VisitBookingStatus.CONFIRMED)
            .limit(5)
            .map(v -> {

                Slot slot =
                        slotRepository
                                .findBySlotId(v.getSlotId())
                                .orElse(null);

                String donorName = donorProfileRepository
                        .findByCareBridgeID(
                                v.getDonorCareBridgeId())
                        .map(DonorProfile::getName)
                        .orElse("Unknown Donor");

                return VisitSummaryDTO.builder()
                        .bookingId(v.getId())
                        .donorName(donorName)
                        .purpose(v.getMessage())
                        .visitDate(
                                slot != null
                                        ? slot.getDate()
                                        : null)
                        .visitTime(
                                slot != null
                                        ? slot.getStartTime()
                                        .toString()
                                        : "")
                        .status(v.getBookingStatus())
                        .build();
            })
            .toList();
}

private List<DonationSummaryDTO> buildRecentDonations(
        String orphanageCareBridgeId) {

    return itemDeliveredResponseRepository
            .findTop5ByOrpIdOrderByCreatedAtDesc(
                    orphanageCareBridgeId)
            .stream()
            .map(d -> {

                String donorName =
                        donorProfileRepository
                                .findByCareBridgeID(
                                        d.getDonorId())
                                .map(DonorProfile::getName)
                                .orElse("Unknown Donor");

                String itemName =
                        needItemRepository
                                .findByNeedItemId(
                                        d.getNeedId())
                                .map(NeedItem::getName)
                                .orElse("Unknown Item");

                Double amount =
                        needItemRepository
                                .findByNeedItemId(
                                        d.getNeedId())
                                .map(n ->
                                        n.getPricePerQuantity()
                                                .multiply(
                                                        BigDecimal.valueOf(
                                                                d.getQuantity()))
                                                .doubleValue())
                                .orElse(0.0);

                return DonationSummaryDTO.builder()
                        .donationId(d.getId())
                        .donorName(donorName)
                        .itemName(itemName)
                        .quantity(d.getQuantity())
                        .amount(amount)
                        .donatedAt(d.getCreatedAt())
                        .build();
            })
            .toList();
}


private List<ActivityDTO> buildRecentActivities(
        String orphanageCareBridgeId,
        List<DonationSummaryDTO> donations) {

    return donations.stream()
            .limit(5)
            .map(d ->
                    ActivityDTO.builder()
                            .activityType(
                                    ActivityType.DONATION_RECEIVED)
                            .title("Donation Received")
                            .description(
                                    d.getDonorName()
                                            +
                                            " donated "
                                            +
                                            d.getItemName())
                            .createdAt(
                                    d.getDonatedAt())
                            .build())
            .toList();
}



}
