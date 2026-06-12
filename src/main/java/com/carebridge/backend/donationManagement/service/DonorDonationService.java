package com.carebridge.backend.donationManagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.carebridge.backend.adminManagement.exception.DonorProfileNotFoundException;
import com.carebridge.backend.authManagement.entity.User;
import com.carebridge.backend.authManagement.repository.UserRepository;
import com.carebridge.backend.common.service.ImageUploadService;
import com.carebridge.backend.donationManagement.dto.DonationDTO;
// import com.carebridge.backend.donationManagement.dto.DonationRequestDTO;
import com.carebridge.backend.donationManagement.dto.DonationResponse;
import com.carebridge.backend.donationManagement.dto.DonationUpdateRequest;
import com.carebridge.backend.donationManagement.entity.DonationRequest;
import com.carebridge.backend.donationManagement.enums.DonationStatus;
import com.carebridge.backend.donationManagement.enums.DonationType;
import com.carebridge.backend.donationManagement.repository.DonationRequestRepo;
import com.carebridge.backend.donorManagement.entity.DonorProfile;
import com.carebridge.backend.donorManagement.exception.UserNotFoundException;
import com.carebridge.backend.donorManagement.repository.DonorProfileRepository;
import com.carebridge.backend.needsManagement.entity.NeedItem;
import com.carebridge.backend.needsManagement.exception.CommonException;
import com.carebridge.backend.needsManagement.exception.ItemNotFound;
import com.carebridge.backend.needsManagement.repository.NeedItemRepo;
import com.carebridge.backend.notificationManagement.service.EmailService;
import com.carebridge.backend.orphanageManagement.entity.OrphanageProfile;
import com.carebridge.backend.orphanageManagement.exception.OrphanageProfileNotFoundException;
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
    private final ImageUploadService imageUploadService;


    public List<DonationDTO> getMyPendingDonations(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("user not found"));
        DonorProfile profile = donorProfileRepository.findByUser(user).orElseThrow(()-> new DonorProfileNotFoundException("donor not found"));
        String donorId = profile.getCareBridgeID();

        List<DonationRequest> list = donationRequestRepo.findByDonorCareBridgeIdAndDonationStatus(donorId, DonationStatus.PENDING);
        List<DonationDTO> pendingDonations = new ArrayList<>();

        for(DonationRequest request : list){
            DonationDTO requestDTO = new DonationDTO();
            requestDTO.setDonationId(request.getDonationRequestId());
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

    public List<DonationDTO> getMyCompletedDonations(){
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("user not found"));
        DonorProfile profile = donorProfileRepository.findByUser(user).orElseThrow(()-> new DonorProfileNotFoundException("donor not found"));
        String donorId = profile.getCareBridgeID();

        List<DonationRequest> list = donationRequestRepo.findByDonorCareBridgeIdAndDonationStatus(donorId, DonationStatus.DELIVERED);
        List<DonationDTO> completedDonations = new ArrayList<>();

        for(DonationRequest request : list){
            DonationDTO requestDTO = new DonationDTO();
            requestDTO.setDonationId(request.getDonationRequestId());
            requestDTO.setNeedItemId(request.getNeedItemId());
            requestDTO.setDonationType(request.getDonationType());
            requestDTO.setExpectedDeliveryDate(request.getExpectedDeliveryDate());
            requestDTO.setExpectedVisitDateTime(request.getExpectedVisitDateTime());
            requestDTO.setMessage(request.getMessage());
            requestDTO.setOrderPicString(request.getOrderProofImageUrl());
            requestDTO.setPlatformName(request.getPlatformName());
            requestDTO.setTrackingId(request.getTrackingId());
            completedDonations.add(requestDTO);
        }
        return completedDonations;
    }


    @Transactional
    public DonationResponse deleteDonation(String id){
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("user not found"));
        DonorProfile profile = donorProfileRepository.findByUser(user).orElseThrow(()-> new DonorProfileNotFoundException("donor not found"));
        String donorId = profile.getCareBridgeID();
        DonationRequest donation = donationRequestRepo.findByDonationRequestIdAndDonorCareBridgeId(id, donorId).orElseThrow(()-> new CommonException("You cannot delete this"));
        if(donation.getDonationStatus().equals(DonationStatus.CANCELLED)){
            throw new CommonException("this donation already cancelled");
        }
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

    public List<DonationDTO> getMyCancelledDonations(){
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("user not found"));
        DonorProfile profile = donorProfileRepository.findByUser(user).orElseThrow(()-> new DonorProfileNotFoundException("donor not found"));
        String donorId = profile.getCareBridgeID();

        List<DonationRequest> list = donationRequestRepo.findByDonorCareBridgeIdAndDonationStatus(donorId, DonationStatus.CANCELLED);
        List<DonationDTO> cancelledDonations = new ArrayList<>();

        for(DonationRequest request : list){
            DonationDTO requestDTO = new DonationDTO();
            requestDTO.setDonationId(request.getDonationRequestId());
            requestDTO.setNeedItemId(request.getNeedItemId());
            requestDTO.setDonationType(request.getDonationType());
            requestDTO.setExpectedDeliveryDate(request.getExpectedDeliveryDate());
            requestDTO.setExpectedVisitDateTime(request.getExpectedVisitDateTime());
            requestDTO.setMessage(request.getMessage());
            requestDTO.setOrderPicString(request.getOrderProofImageUrl());
            requestDTO.setPlatformName(request.getPlatformName());
            requestDTO.setTrackingId(request.getTrackingId());
            cancelledDonations.add(requestDTO);
        }
        return cancelledDonations;
    }


     public List<DonationDTO> getMyDeliveredDonations(){
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("user not found"));
        DonorProfile profile = donorProfileRepository.findByUser(user).orElseThrow(()-> new DonorProfileNotFoundException("donor not found"));
        String donorId = profile.getCareBridgeID();

        List<DonationRequest> list = donationRequestRepo.findByDonorCareBridgeIdAndDonationStatus(donorId, DonationStatus.DELIVERED);
        List<DonationDTO> deliveredDonations = new ArrayList<>();

        for(DonationRequest request : list){
            DonationDTO requestDTO = new DonationDTO();
            requestDTO.setDonationId(request.getDonationRequestId());
            requestDTO.setNeedItemId(request.getNeedItemId());
            requestDTO.setDonationType(request.getDonationType());
            requestDTO.setExpectedDeliveryDate(request.getExpectedDeliveryDate());
            requestDTO.setExpectedVisitDateTime(request.getExpectedVisitDateTime());
            requestDTO.setMessage(request.getMessage());
            requestDTO.setOrderPicString(request.getOrderProofImageUrl());
            requestDTO.setPlatformName(request.getPlatformName());
            requestDTO.setTrackingId(request.getTrackingId());
            deliveredDonations.add(requestDTO);
        }
        return deliveredDonations;
    }

   @Transactional
public DonationResponse updateDonation(
        DonationUpdateRequest request,
        String donationId
){

    //  authenticated donor

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
                                    "user not found"
                            ));

    DonorProfile profile =
            donorProfileRepository
                    .findByUser(user)
                    .orElseThrow(() ->
                            new DonorProfileNotFoundException(
                                    "donor not found"
                            ));

    String donorId =
            profile.getCareBridgeID();

    //  fetch donation

    DonationRequest donation =
            donationRequestRepo
                    .findByDonationRequestIdAndDonorCareBridgeId(
                            donationId,
                            donorId
                    )
                    .orElseThrow(() ->
                            new CommonException(
                                    "donation not found"
                            ));

    //  only pending donations editable

    if(!(donation.getDonationStatus()
            .equals(DonationStatus.PENDING))){

        throw new CommonException(
                "Cannot update this donation"
        );
    }

    //  fetch need item with lock

    NeedItem item =
            needItemRepo
                    .findByNeedItemId(
                            donation.getNeedItemId()
                    )
                    .orElseThrow(() ->
                            new ItemNotFound(
                                    "Need item not found"
                            ));

    OrphanageProfile orpProfile = orphanageProfileRepository.findByCarebridgeId(item.getOrphanageCareBridgeId()).orElseThrow(()-> new OrphanageProfileNotFoundException("orp profile not found"));
    // old quantity

    int oldQuantity =
            donation.getQuantity();

    //  new quantity

    int newQuantity =
            request.getQuantity();

    //  difference

    int difference =
            newQuantity - oldQuantity;

    //  calculate remaining quantity

    int remaining =
            item.getQuantity()
            -
            item.getReservedQuantity()
            -
            item.getFulfilledQuantity();

    //  if increasing quantity

    if(difference > 0){

        if(difference > remaining){

            throw new CommonException(
                    "Requested quantity exceeds available quantity"
            );
        }

        item.setReservedQuantity(
                item.getReservedQuantity()
                +
                difference
        );
    }

    //  if decreasing quantity

    if(difference < 0){

        item.setReservedQuantity(
                item.getReservedQuantity()
                -
                Math.abs(difference)
        );
    }

    //  update quantity

    donation.setQuantity(newQuantity);

    //  update donation type

    donation.setDonationType(
            request.getDonationType()
    );

    // =====================================================
    //  IN_PERSON
    // =====================================================

    if(request.getDonationType()
            == DonationType.IN_PERSON){

        if(request.getExpectedVisitDateTime() == null){

            throw new CommonException(
                    "Expected visit date/time required"
            );
        }

        donation.setExpectedVisitDateTime(
                request.getExpectedVisitDateTime()
        );

        // clear online fields

        donation.setExpectedDeliveryDate(null);

        donation.setOrderProofImageUrl(null);

        donation.setPlatformName(null);

        donation.setTrackingId(null);
    }

    // =====================================================
    //  ONLINE_ORDER
    // =====================================================

    if(request.getDonationType()
            == DonationType.ONLINE_ORDER){

        if(request.getExpectedDeliveryDate() == null){

            throw new CommonException(
                    "Expected delivery date required"
            );
        }

        donation.setExpectedDeliveryDate(
                request.getExpectedDeliveryDate()
        );

        donation.setPlatformName(
                request.getPlatformName()
        );

        donation.setTrackingId(
                request.getTrackingId()
        );

        //  upload new proof image

        if(request.getOrderProofImage() != null
                &&
                !request.getOrderProofImage().isEmpty()){

            CompletableFuture<String> imageUrl =
                    imageUploadService
                            .uploadImageAsync(
                                    request.getOrderProofImage()
                            );

            donation.setOrderProofImageUrl(
                    imageUrl.join()
            );
        }

        // clear in-person field

        donation.setExpectedVisitDateTime(null);
    }

    //  update optional message

    if(request.getMessage() != null
            &&
            !request.getMessage().isBlank()){

        donation.setMessage(
                request.getMessage()
        );
    }

    //  save

    donationRequestRepo.save(donation);

    needItemRepo.save(item);

emailService.donationUpdateNotification(
        orpProfile.getOrphanageEmail(),
        donation.getDonationRequestId(),
        donorId,
        profile.getName(),
        donation.getNeedItemId(),
        item.getName(),
        donation.getDonationType().toString(),
        donation.getQuantity(),
        item.getQuantity()
                - item.getReservedQuantity()
                - item.getFulfilledQuantity(),
        donation.getExpectedVisitDateTime() != null
                ? donation.getExpectedVisitDateTime().toString()
                : "N/A",
        donation.getExpectedDeliveryDate() != null
                ? donation.getExpectedDeliveryDate().toString()
                : "N/A"
);

    return new DonationResponse(
            "Donation updated successfully",
            donationId
    );
}



}
