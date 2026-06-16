package com.carebridge.backend.dashBoardManagement.service;

// import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.carebridge.backend.authManagement.entity.User;
import com.carebridge.backend.authManagement.exception.UserNotFoundException;
import com.carebridge.backend.authManagement.repository.UserRepository;
import com.carebridge.backend.dashBoardManagement.dto.DonorDashBoard;
import com.carebridge.backend.dashBoardManagement.dto.DonorData;
import com.carebridge.backend.dashBoardManagement.dto.DonorImpactSummary;
import com.carebridge.backend.dashBoardManagement.dto.DonorStats;
import com.carebridge.backend.dashBoardManagement.dto.RecentDonations;
import com.carebridge.backend.dashBoardManagement.dto.UpcomingBookings;
import com.carebridge.backend.dashBoardManagement.dto.UrgentNeeds;
import com.carebridge.backend.donationManagement.entity.DonationRequest;
import com.carebridge.backend.donationManagement.enums.DonationStatus;
import com.carebridge.backend.donationManagement.repository.DonationRequestRepo;
import com.carebridge.backend.donorManagement.entity.DonorProfile;
import com.carebridge.backend.donorManagement.repository.DonorProfileRepository;
import com.carebridge.backend.needsManagement.entity.NeedItem;
import com.carebridge.backend.needsManagement.repository.NeedItemRepo;
import com.carebridge.backend.orphanageManagement.exception.OrphanageProfileNotFoundException;
import com.carebridge.backend.orphanageManagement.repository.OrphanageProfileRepository;
import com.carebridge.backend.visitbookingManagement.entity.VisitBooking;
import com.carebridge.backend.visitbookingManagement.enums.VisitBookingStatus;
import com.carebridge.backend.visitbookingManagement.repository.VisitBookingRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DonorDashBoardService {
    
    private final UserRepository userRepository;
    private final DonorProfileRepository donorProfileRepository;
    private final DonorStatsService donorStatsService; 
    private final NeedItemRepo needItemRepo;
    private final OrphanageProfileRepository orphanageProfileRepository;
    private final VisitBookingRepo visitBookingRepo;
    private final DonationRequestRepo donationRequestRepo;
    private final DonorImpactService donorImpactService;

    public DonorDashBoard getDonorDashBoard() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not present"));
        DonorProfile profile = donorProfileRepository.findByUser(user).orElseThrow(()-> new OrphanageProfileNotFoundException("profile not found"));
        String donorId = profile.getCareBridgeID();

        //donor data
        DonorData donorData = new DonorData();
        donorData.setCareId(profile.getCareBridgeID());
        donorData.setName(profile.getName());
        donorData.setRole(user.getRole().name());
        donorData.setImagePath(profile.getProfilePic());

        //donor stats
        DonorStats donorStats = new DonorStats();
        donorStats.setTotalDonationAmmount(donorStatsService.getTotalAmmount(profile.getCareBridgeID()));
        donorStats.setNeedsSupported(donorStatsService.getTotalNeedsSupported());
        donorStats.setTotalVisits(donorStatsService.totalVisitsBooked(profile.getCareBridgeID()));
        donorStats.setOrphanageSupported(donorStatsService.orphanegeSupported(profile.getCareBridgeID()));

        //urgent needs
        List<NeedItem> allUrgentNeeds = needItemRepo.getUrgentNeeds();
        List<UrgentNeeds> urNeeds = new ArrayList<>();
        for(NeedItem urgentNeed : allUrgentNeeds){
            UrgentNeeds need = new UrgentNeeds();
            need.setNeedId(urgentNeed.getNeedItemId());
            need.setCategory(urgentNeed.getCategory());
            need.setOrpName(orphanageProfileRepository.getOrpName(urgentNeed.getOrphanageCareBridgeId()));
            need.setPriorityLevel(urgentNeed.getPriority().name());
            need.setQuantityNeeded((urgentNeed.getQuantity() - urgentNeed.getReservedQuantity() - urgentNeed.getFulfilledQuantity())+"");
            need.setTitle(urgentNeed.getDescription());
            urNeeds.add(need);
        }

        //upcoming bookings
        List<UpcomingBookings> upcomingBookings = new ArrayList<>();
        List<VisitBooking> visitsData = visitBookingRepo.getDonorUpcomingBookings(donorId);
        for(VisitBooking visitData : visitsData){
            UpcomingBookings upcomingBooking = new UpcomingBookings();
            upcomingBooking.setBookingDate(visitData.getCreatedAt().toString());
            upcomingBooking.setBookingId(visitData.getBookingId());
            upcomingBooking.setBookingStatus(visitData.getBookingStatus());
            upcomingBooking.setBookingTime(visitData.getBookingId());
            upcomingBooking.setOrpName(orphanageProfileRepository.getOrpName(visitData.getOrphanageCareBridgeId()));
            upcomingBooking.setTitle(visitData.getMessage());
            upcomingBookings.add(upcomingBooking);
        }

        //recent donations
        List<DonationRequest> donationsDone = donationRequestRepo.findByDonorCareBridgeIdAndDonationStatus(donorId, DonationStatus.DELIVERED);
        List<RecentDonations> recentDonations = new ArrayList<>();
        for(DonationRequest donationDone : donationsDone){
            RecentDonations recentDonation = new RecentDonations();
            recentDonation.setDonationDate(donationDone.getUpdatedAt().toString());
            recentDonation.setDonationId(donationDone.getDonationRequestId());
            recentDonation.setOrpName(orphanageProfileRepository.getOrpName(donationDone.getOrphanageCareBridgeId()));
            recentDonation.setImagePath(donationDone.getOrderProofImageUrl());
            recentDonation.setItemName(needItemRepo.getItemName(donationDone.getNeedItemId()));
            recentDonations.add(recentDonation);
        }

        // donor impact
        DonorImpactSummary donorImpactSummary = new DonorImpactSummary();
        donorImpactSummary.setItemsDonated(donorStatsService.getTotalNeedsSupported());
        donorImpactSummary.setChildrenImpacted(donorImpactService.totalChildrenImpacted(donorId));
        donorImpactSummary.setVisitsMade(visitBookingRepo.countByDonorCareBridgeIdAndVisitBookingStatus(donorId, VisitBookingStatus.COMPLETED));

        return new DonorDashBoard(donorData,donorStats,donorImpactSummary,recentDonations, upcomingBookings,urNeeds);
    }
}
