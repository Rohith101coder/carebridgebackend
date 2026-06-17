package com.carebridge.backend.dashBoardManagement.donordashboardManagement.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.carebridge.backend.donationManagement.entity.DonationRequest;
import com.carebridge.backend.donationManagement.enums.DonationStatus;
import com.carebridge.backend.donationManagement.repository.DonationRequestRepo;
import com.carebridge.backend.needsManagement.entity.NeedItem;
import com.carebridge.backend.needsManagement.exception.CommonException;
import com.carebridge.backend.needsManagement.repository.NeedItemRepo;
import com.carebridge.backend.visitbookingManagement.enums.VisitBookingStatus;
import com.carebridge.backend.visitbookingManagement.repository.VisitBookingRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DonorStatsService {
    
    private final DonationRequestRepo donationRequestRepo;
    private final NeedItemRepo needItemRepo;
    private final VisitBookingRepo visitBookingRepo;

    int totalNeedsSupported = 0;
    public int getTotalAmmount(String careId){
        List<DonationRequest> donationList = donationRequestRepo.findByDonorCareBridgeIdAndDonationStatus(careId, DonationStatus.DELIVERED);
        BigDecimal totalAmmount = new BigDecimal(0);
        for(DonationRequest donation : donationList){
            String itemId = donation.getNeedItemId();
            NeedItem item = needItemRepo.findByNeedItemId(itemId).orElseThrow(()-> new CommonException("item not found"));
            totalAmmount= totalAmmount.add(item.getPricePerQuantity());
        }
        totalNeedsSupported = donationList.size();

        return totalAmmount.intValue();
    }

    public int getTotalNeedsSupported(){
        return totalNeedsSupported;
    }

    public int totalVisitsBooked(String careId){
        return visitBookingRepo.countByDonorCareBridgeIdAndBookingStatus(careId, VisitBookingStatus.COMPLETED )
        +visitBookingRepo.countByDonorCareBridgeIdAndBookingStatus(careId, VisitBookingStatus.CONFIRMED)
        +visitBookingRepo.countByDonorCareBridgeIdAndBookingStatus(careId, VisitBookingStatus.PENDING)
        ;
    }

    public int orphanegeSupported(String careId){

        return donationRequestRepo.getOrps(careId);
    }
}
