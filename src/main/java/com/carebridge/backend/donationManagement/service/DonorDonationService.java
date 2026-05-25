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
import com.carebridge.backend.donationManagement.entity.DonationRequest;
import com.carebridge.backend.donationManagement.enums.DonationStatus;
import com.carebridge.backend.donationManagement.repository.DonationRequestRepo;
import com.carebridge.backend.donorManagement.entity.DonorProfile;
import com.carebridge.backend.donorManagement.exception.UserNotFoundException;
import com.carebridge.backend.donorManagement.repository.DonorProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DonorDonationService {
    

    private final DonationRequestRepo donationRequestRepo;
    private final UserRepository userRepository;
    private final DonorProfileRepository donorProfileRepository;

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
}
