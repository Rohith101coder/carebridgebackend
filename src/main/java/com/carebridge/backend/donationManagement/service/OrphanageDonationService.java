package com.carebridge.backend.donationManagement.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.carebridge.backend.authManagement.entity.User;
import com.carebridge.backend.authManagement.exception.UserNotFoundException;
import com.carebridge.backend.authManagement.repository.UserRepository;
import com.carebridge.backend.common.service.ImageUploadService;
import com.carebridge.backend.donationManagement.dto.DonationResponse;
import com.carebridge.backend.donationManagement.dto.ItemDeliveredDTO;
import com.carebridge.backend.donationManagement.dto.OrphanageDonationResponse;
import com.carebridge.backend.donationManagement.entity.DonationRequest;
import com.carebridge.backend.donationManagement.entity.ItemDeliveredResponse;
import com.carebridge.backend.donationManagement.enums.DonationStatus;
import com.carebridge.backend.donationManagement.repository.DonationRequestRepo;
import com.carebridge.backend.donationManagement.repository.ItemDeliveredRepo;
import com.carebridge.backend.donorManagement.entity.DonorProfile;
import com.carebridge.backend.donorManagement.repository.DonorProfileRepository;
import com.carebridge.backend.needsManagement.entity.NeedItem;
import com.carebridge.backend.needsManagement.exception.CommonException;
// import com.carebridge.backend.needsManagement.exception.ItemNotFound;
import com.carebridge.backend.needsManagement.repository.NeedItemRepo;
import com.carebridge.backend.notificationManagement.service.EmailService;
import com.carebridge.backend.orphanageManagement.entity.OrphanageProfile;
import com.carebridge.backend.orphanageManagement.exception.OrphanageProfileNotFoundException;
import com.carebridge.backend.orphanageManagement.repository.OrphanageProfileRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrphanageDonationService {

    private final DonationRequestRepo donationRequestRepo;
    private final UserRepository userRepository;
    private final OrphanageProfileRepository orphanageProfileRepository;
    private final DonorProfileRepository donorProfileRepository;
    private final NeedItemRepo needItemRepo;
    private final ImageUploadService imageUploadService;
    private final ItemDeliveredRepo itemDeliveredRepo;
    private final EmailService emailService;
    

   public List<OrphanageDonationResponse> getPendingDonations(){

    // ✅ authenticated orphanage

    Authentication authentication =
            SecurityContextHolder
                    .getContext()
                    .getAuthentication();

    String email = authentication.getName();

    User user =
            userRepository
                    .findByEmail(email)
                    .orElseThrow(() ->
                            new UserNotFoundException(
                                    "User not found"
                            ));

    OrphanageProfile orphanage =
            orphanageProfileRepository
                    .findByUser(user)
                    .orElseThrow(() ->
                            new OrphanageProfileNotFoundException(
                                    "Orphanage not found"
                            ));

    // ✅ fetch pending donations

    List<DonationRequest> donations =
            donationRequestRepo
                    .findByOrphanageCareBridgeIdAndDonationStatus(
                            orphanage.getCarebridgeId(),
                            DonationStatus.PENDING
                    );
    
        List<OrphanageDonationResponse> pendings = new ArrayList<>();

    // ✅ map response
    for(DonationRequest donation : donations){
        OrphanageDonationResponse response = new OrphanageDonationResponse();
        response.setDonationRequestId(donation.getDonationRequestId());
        response.setDonationStatus(donation.getDonationStatus());
        response.setDonationType(donation.getDonationType());
        response.setDonatedQuantity(donation.getQuantity());
        response.setDonorMessage(donation.getMessage());
        response.setNeedItemId(donation.getNeedItemId());
        NeedItem item = needItemRepo.findByNeedItemId(donation.getNeedItemId()).orElseThrow(()-> new CommonException("item not found"));
        response.setItemName(item.getName());
        response.setItemDescription(item.getDescription());
        response.setCategory(item.getCategory());
        response.setPriority(item.getPriority());
        response.setTotalRequiredQuantity(item.getQuantity());
        response.setReservedQuantity(item.getReservedQuantity());
        response.setFulfilledQuantity(item.getFulfilledQuantity());
        response.setPricePerQuantity(item.getPricePerQuantity());
        response.setProductLinks(item.getProductLinks());
        DonorProfile donor = donorProfileRepository.findByCareBridgeID(donation.getDonorCareBridgeId()).orElseThrow(()-> new CommonException("donor not found"));
        response.setDonorCareBridgeId(donation.getDonorCareBridgeId());
        response.setDonorName(donor.getName());
        response.setDonorPhone(donor.getPhone());
        response.setDonorEmail(donor.getUser().getEmail());
        response.setExpectedVisitDateTime(donation.getExpectedVisitDateTime());
        response.setExpectedDeliveryDate(donation.getExpectedDeliveryDate());
        response.setOrderProofImageUrl(donation.getOrderProofImageUrl());
        response.setPlatformName(donation.getPlatformName());
        response.setTrackingId(donation.getTrackingId());
        response.setCreatedAt(donation.getCreatedAt());
        response.setUpdatedAt(donation.getUpdatedAt());

        pendings.add(response);
        
    }

    return pendings;
                }





        public List<OrphanageDonationResponse> getDeliveredDonations(){

    // ✅ authenticated orphanage

    Authentication authentication =
            SecurityContextHolder
                    .getContext()
                    .getAuthentication();

    String email = authentication.getName();

    User user =
            userRepository
                    .findByEmail(email)
                    .orElseThrow(() ->
                            new UserNotFoundException(
                                    "User not found"
                            ));

    OrphanageProfile orphanage =
            orphanageProfileRepository
                    .findByUser(user)
                    .orElseThrow(() ->
                            new OrphanageProfileNotFoundException(
                                    "Orphanage not found"
                            ));

    // ✅ fetch pending donations

    List<DonationRequest> donations =
            donationRequestRepo
                    .findByOrphanageCareBridgeIdAndDonationStatus(
                            orphanage.getCarebridgeId(),
                            DonationStatus.DELIVERED
                    );
    
        List<OrphanageDonationResponse> delivered = new ArrayList<>();

    // ✅ map response
    for(DonationRequest donation : donations){
        OrphanageDonationResponse response = new OrphanageDonationResponse();
        response.setDonationRequestId(donation.getDonationRequestId());
        response.setDonationStatus(donation.getDonationStatus());
        response.setDonationType(donation.getDonationType());
        response.setDonatedQuantity(donation.getQuantity());
        response.setDonorMessage(donation.getMessage());
        response.setNeedItemId(donation.getNeedItemId());
        NeedItem item = needItemRepo.findByNeedItemId(donation.getNeedItemId()).orElseThrow(()-> new CommonException("item not found"));
        response.setItemName(item.getName());
        response.setItemDescription(item.getDescription());
        response.setCategory(item.getCategory());
        response.setPriority(item.getPriority());
        response.setTotalRequiredQuantity(item.getQuantity());
        response.setReservedQuantity(item.getReservedQuantity());
        response.setFulfilledQuantity(item.getFulfilledQuantity());
        response.setPricePerQuantity(item.getPricePerQuantity());
        response.setProductLinks(item.getProductLinks());
        DonorProfile donor = donorProfileRepository.findByCareBridgeID(donation.getDonorCareBridgeId()).orElseThrow(()-> new CommonException("donor not found"));
        response.setDonorCareBridgeId(donation.getDonorCareBridgeId());
        response.setDonorName(donor.getName());
        response.setDonorPhone(donor.getPhone());
        response.setDonorEmail(donor.getUser().getEmail());
        response.setExpectedVisitDateTime(donation.getExpectedVisitDateTime());
        response.setExpectedDeliveryDate(donation.getExpectedDeliveryDate());
        response.setOrderProofImageUrl(donation.getOrderProofImageUrl());
        response.setPlatformName(donation.getPlatformName());
        response.setTrackingId(donation.getTrackingId());
        response.setCreatedAt(donation.getCreatedAt());
        response.setUpdatedAt(donation.getUpdatedAt());

        delivered.add(response);
        
    }

    return delivered;
                }




                public List<OrphanageDonationResponse> getCancelledDonations(){

    // ✅ authenticated orphanage

    Authentication authentication =
            SecurityContextHolder
                    .getContext()
                    .getAuthentication();

    String email = authentication.getName();

    User user =
            userRepository
                    .findByEmail(email)
                    .orElseThrow(() ->
                            new UserNotFoundException(
                                    "User not found"
                            ));

    OrphanageProfile orphanage =
            orphanageProfileRepository
                    .findByUser(user)
                    .orElseThrow(() ->
                            new OrphanageProfileNotFoundException(
                                    "Orphanage not found"
                            ));

    // ✅ fetch pending donations

    List<DonationRequest> donations =
            donationRequestRepo
                    .findByOrphanageCareBridgeIdAndDonationStatus(
                            orphanage.getCarebridgeId(),
                            DonationStatus.CANCELLED
                    );
    
        List<OrphanageDonationResponse> cancelled = new ArrayList<>();

    // ✅ map response
    for(DonationRequest donation : donations){
        OrphanageDonationResponse response = new OrphanageDonationResponse();
        response.setDonationRequestId(donation.getDonationRequestId());
        response.setDonationStatus(donation.getDonationStatus());
        response.setDonationType(donation.getDonationType());
        response.setDonatedQuantity(donation.getQuantity());
        response.setDonorMessage(donation.getMessage());
        response.setNeedItemId(donation.getNeedItemId());
        NeedItem item = needItemRepo.findByNeedItemId(donation.getNeedItemId()).orElseThrow(()-> new CommonException("item not found"));
        response.setItemName(item.getName());
        response.setItemDescription(item.getDescription());
        response.setCategory(item.getCategory());
        response.setPriority(item.getPriority());
        response.setTotalRequiredQuantity(item.getQuantity());
        response.setReservedQuantity(item.getReservedQuantity());
        response.setFulfilledQuantity(item.getFulfilledQuantity());
        response.setPricePerQuantity(item.getPricePerQuantity());
        response.setProductLinks(item.getProductLinks());
        DonorProfile donor = donorProfileRepository.findByCareBridgeID(donation.getDonorCareBridgeId()).orElseThrow(()-> new CommonException("donor not found"));
        response.setDonorCareBridgeId(donation.getDonorCareBridgeId());
        response.setDonorName(donor.getName());
        response.setDonorPhone(donor.getPhone());
        response.setDonorEmail(donor.getUser().getEmail());
        response.setExpectedVisitDateTime(donation.getExpectedVisitDateTime());
        response.setExpectedDeliveryDate(donation.getExpectedDeliveryDate());
        response.setOrderProofImageUrl(donation.getOrderProofImageUrl());
        response.setPlatformName(donation.getPlatformName());
        response.setTrackingId(donation.getTrackingId());
        response.setCreatedAt(donation.getCreatedAt());
        response.setUpdatedAt(donation.getUpdatedAt());

        cancelled.add(response);
        
    }

    return cancelled;
                }


                @Transactional
    public DonationResponse acceptDonation(String id, ItemDeliveredDTO
         deliveredResponse){
         Authentication authentication =
            SecurityContextHolder
                    .getContext()
                    .getAuthentication();

    String email = authentication.getName();
    System.out.println(email);
    User user = userRepository.findByEmail(email).orElseThrow(()-> new CommonException("user not found"));
    OrphanageProfile orpProfile = orphanageProfileRepository.findByUser(user).orElseThrow(()-> new CommonException("orp not found"));

    DonationRequest donation = donationRequestRepo.findByDonationRequestIdAndOrphanageCareBridgeId(id, orpProfile.getCarebridgeId());

    DonorProfile donProfile = donorProfileRepository.findByCareBridgeID(donation.getDonorCareBridgeId()).orElseThrow(()-> new CommonException("donor not found"));

    NeedItem item  =needItemRepo.findByNeedItemId(donation.getNeedItemId()).orElseThrow(() -> new CommonException("item not found"));

    item.setFulfilledQuantity(item.getFulfilledQuantity()+donation.getQuantity());
    item.setReservedQuantity(item.getReservedQuantity() - donation.getQuantity());

    donation.setDonationStatus(DonationStatus.DELIVERED);

    CompletableFuture<String> deliveredPhoto = imageUploadService.uploadImageAsync(deliveredResponse.getDeliveredPhoto());

            ItemDeliveredResponse itemResponse = new ItemDeliveredResponse();
            itemResponse.setDonationId(id);
            itemResponse.setNeedId(item.getNeedItemId());
            itemResponse.setDonorId(donProfile.getCareBridgeID());
            itemResponse.setOrpId(donation.getOrphanageCareBridgeId());
            itemResponse.setNote(deliveredResponse.getNote());
            itemResponse.setDeliveredItemPhoto(deliveredPhoto.join());
            itemResponse.setQuantity(donation.getQuantity());

            needItemRepo.save(item);
            donationRequestRepo.save(donation);
            itemDeliveredRepo.save(itemResponse);
            String donationInitiatedDate = donation.getCreatedAt().toString();

            emailService.donationDeliveredNotification(donProfile.getUser().getEmail(),id,orpProfile.getCarebridgeId(),
            orpProfile.getOrphanageName(),donation.getQuantity().toString(),item.getName(),donation.getDonationStatus().toString(),deliveredResponse.getNote(),deliveredPhoto.join(),LocalDate.now().toString(),donationInitiatedDate);

        return new DonationResponse("donation accepted", id);
    }


}
