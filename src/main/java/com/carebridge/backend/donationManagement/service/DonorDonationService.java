package com.carebridge.backend.donationManagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.carebridge.backend.adminManagement.exception.DonorProfileNotFoundException;
import com.carebridge.backend.authManagement.entity.User;
import com.carebridge.backend.authManagement.repository.UserRepository;
import com.carebridge.backend.donationManagement.dto.DonationRequestDTO;
import com.carebridge.backend.donationManagement.dto.DonationResponse;
import com.carebridge.backend.donationManagement.entity.DonationRequest;
import com.carebridge.backend.donationManagement.enums.DonationStatus;
import com.carebridge.backend.donationManagement.repository.DonationRequestRepo;
import com.carebridge.backend.donorManagement.entity.DonorProfile;
import com.carebridge.backend.donorManagement.exception.UserNotFoundException;
import com.carebridge.backend.donorManagement.repository.DonorProfileRepository;
import com.carebridge.backend.needsManagement.entity.NeedItem;
import com.carebridge.backend.needsManagement.exception.CommonException;
import com.carebridge.backend.needsManagement.repository.NeedItemRepo;
import com.carebridge.backend.notificationManagement.service.EmailService;
import com.carebridge.backend.orphanageManagement.entity.OrphanageProfile;
import com.carebridge.backend.orphanageManagement.repository.OrphanageProfileRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DonorDonationService {
    

    private final DonationRequestRepo donationRequestRepo;
    private final UserRepository userRepository;
    private final DonorProfileRepository donorProfileRepository;
    private final NeedItemRepo needItemRepo;
    private final OrphanageProfileRepository orphanageProfileRepository;
    private final EmailService emailService;


    public List<DonationRequestDTO> getMyPendingDonations(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("user not found"));
        DonorProfile profile = donorProfileRepository.findByUser(user).orElseThrow(()-> new DonorProfileNotFoundException("donor not found"));
        String donorId = profile.getCareBridgeID();

        List<DonationRequest> list = donationRequestRepo.findByDonorCareBridgeIdAndDonationStatus(donorId, DonationStatus.PENDING);
        List<DonationRequestDTO> pendingDonations = new ArrayList<>();

        for(DonationRequest request : list){
            DonationRequestDTO requestDTO = new DonationRequestDTO();
            requestDTO.setNeedItemId(request.getNeedItemId());
            requestDTO.setDonationType(request.getDonationType());
            requestDTO.setExpectedDeliveryDate(request.getExpectedDeliveryDate());
            requestDTO.setExpectedVisitDateTime(request.getExpectedVisitDateTime());
            requestDTO.setMessage(request.getMessage());
            requestDTO.setOrderPicString(request.getOrderProofImageUrl());
            requestDTO.setPlatformName(request.getPlatformName());
            requestDTO.setTrackingId(request.getTrackingId());
            requestDTO.setQuantity(request.getQuantity());
            pendingDonations.add(requestDTO);
        }
        return pendingDonations;
    }

    public List<DonationRequestDTO> getMyCompletedDonations(){

         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("user not found"));
        DonorProfile profile = donorProfileRepository.findByUser(user).orElseThrow(()-> new DonorProfileNotFoundException("donor not found"));
        String donorId = profile.getCareBridgeID();

        List<DonationRequest> list = donationRequestRepo.findByDonorCareBridgeIdAndDonationStatus(donorId, DonationStatus.DELIVERED);
        List<DonationRequestDTO> pendingDonations = new ArrayList<>();

        for(DonationRequest request : list){
            DonationRequestDTO requestDTO = new DonationRequestDTO();
            requestDTO.setNeedItemId(request.getNeedItemId());
            requestDTO.setDonationType(request.getDonationType());
            requestDTO.setExpectedDeliveryDate(request.getExpectedDeliveryDate());
            requestDTO.setExpectedVisitDateTime(request.getExpectedVisitDateTime());
            requestDTO.setMessage(request.getMessage());
            requestDTO.setOrderPicString(request.getOrderProofImageUrl());
            requestDTO.setPlatformName(request.getPlatformName());
            requestDTO.setTrackingId(request.getTrackingId());
            pendingDonations.add(requestDTO);
        }
        return pendingDonations;
    }


    @Transactional
    public DonationResponse deleteDonation(String id){
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("user not found"));
        DonorProfile profile = donorProfileRepository.findByUser(user).orElseThrow(()-> new DonorProfileNotFoundException("donor not found"));
        String donorId = profile.getCareBridgeID();
        DonationRequest donation = donationRequestRepo.findByDonationRequestIdAndDonorCareBridgeId(id, donorId).orElseThrow(()-> new CommonException("You cannot delete this"));
        NeedItem item = needItemRepo.findByNeedItemId(donation.getNeedItemId()).orElseThrow(()-> new CommonException("item not found"));
        OrphanageProfile orpProfile = orphanageProfileRepository.findByCarebridgeId(donation.getOrphanageCareBridgeId()).orElseThrow(()-> new CommonException("orphanage not found"));
        Integer donorQuantity = donation.getQuantity();
        if(donation != null){
            item.setReservedQuantity(item.getReservedQuantity() - donorQuantity);
            donation.setDonationStatus(DonationStatus.CANCELLED);
            needItemRepo.save(item);
            donationRequestRepo.save(donation);
            String donationId = donation.getDonationRequestId();
            String remaining = (item.getQuantity() - item.getReservedQuantity())+"";
            emailService.donationCancelNotification(orpProfile.getOrphanageEmail(),donationId,donorId,donorQuantity.toString(),remaining);
        }
        return new DonationResponse("donation cancelled",id);
    }
}
