package com.carebridge.backend.donationManagement.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.carebridge.backend.authManagement.entity.User;
import com.carebridge.backend.authManagement.repository.UserRepository;
import com.carebridge.backend.common.service.ImageUploadService;
import com.carebridge.backend.donationManagement.dto.DonationRequestDTO;
import com.carebridge.backend.donationManagement.dto.DonationResponse;
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
public class DonationService {
    
    private final DonationRequestRepo donationRequestRepo;

    private final NeedItemRepo needItemRepo;

    private final UserRepository userRepository;

    private final DonorProfileRepository donorProfileRepository;

    private final OrphanageProfileRepository orphanageProfileRepository;

    private final ImageUploadService imageUploadService;

    private final EmailService emailService;

    @Transactional
    public DonationResponse createDonationRequest(DonationRequestDTO request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("user not found"));

        DonorProfile donor =
                donorProfileRepository
                        .findByUser(user)
                        .orElseThrow(() ->
                                new CommonException(
                                        "Donor profile not found"
                                ));

              NeedItem item =
                needItemRepo
                        .findByNeedItemIdForUpdate(
                                request.getNeedItemId()
                        )
                        .orElseThrow(() ->
                                new ItemNotFound(
                                        "Need item not found"
                                ));


                   OrphanageProfile orphanage =
                orphanageProfileRepository
                        .findByCarebridgeId(
                                item.getOrphanageCareBridgeId()
                        )
                        .orElseThrow(() ->
                                new OrphanageProfileNotFoundException(
                                        "Orphanage not found"
                                ));

              int remainingQuantity =
                item.getQuantity()
                -
                item.getReservedQuantity()
                -
                item.getFulfilledQuantity();


                   if(request.getQuantity() <= 0){

            throw new CommonException(
                    "Quantity must be greater than 0"
            );
        }

          if(request.getQuantity() > remainingQuantity){

            throw new CommonException(
                    "Requested quantity exceeds available quantity"
            );
        }

            DonationRequest donation =
                new DonationRequest();

           String donationRequestId =
                "CB-DR-"
                +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();
        

                          donation.setDonationRequestId(
                donationRequestId
        );

        donation.setNeedItemId(
                item.getNeedItemId()
        );

        donation.setDonorCareBridgeId(
                donor.getCareBridgeID()
        );

        donation.setOrphanageCareBridgeId(
                orphanage.getCarebridgeId()
        );

        donation.setQuantity(
                request.getQuantity()
        );

        donation.setDonationType(
                request.getDonationType()
        );

        donation.setDonationStatus(
                DonationStatus.PENDING
        );

          donation.setMessage(
                request.getMessage()
        );



        //in-person

          if(request.getDonationType()
                == DonationType.IN_PERSON){

            if(request.getExpectedVisitDateTime() == null){

                throw new CommonException(
                        "Expected visit date/time is required"
                );
            }

            donation.setExpectedVisitDateTime(
                    request.getExpectedVisitDateTime()
            );
        }


        //online donation

        if(request.getDonationType()
                == DonationType.ONLINE_ORDER){

            if(request.getExpectedDeliveryDate() == null){

                throw new CommonException(
                        "Expected delivery date is required"
                );
            }

            if(request.getOrderProofImage() == null
                    || request.getOrderProofImage().isEmpty()){

                throw new CommonException(
                        "Order proof image is required"
                );
            }

            CompletableFuture<String> imageUrl =
                    imageUploadService
                            .uploadImageAsync(
                                    request.getOrderProofImage()
                            );

            donation.setOrderProofImageUrl(
                    imageUrl.join()
            );

            donation.setExpectedDeliveryDate(
                    request.getExpectedDeliveryDate()
            );

            donation.setPlatformName(
                    request.getPlatformName()
            );

            donation.setTrackingId(
                    request.getTrackingId()
            );
        }

          item.setReservedQuantity(
                item.getReservedQuantity()
                +
                request.getQuantity()
        );

          donationRequestRepo.save(donation);

        needItemRepo.save(item);
        String orpEmail = orphanage.getOrphanageEmail();
        String donorId = donor.getCareBridgeID();
        String donorName = donor.getName();
        String donorPhone = donor.getPhone();
        String modeOfDonation = request.getDonationType().toString();
        String donationId = donationRequestId;
        String needItemId = item.getNeedItemId();
        String needItemName = item.getName();
        String expectedDeliveryDate = request.getExpectedDeliveryDate()!=null ? request.getExpectedDeliveryDate().toString() : "Ignore this";
        String expectedDonationDate = request.getExpectedVisitDateTime() != null ? request.getExpectedVisitDateTime().toString() :"Ignore this";
        String requiredQuantity = item.getQuantity().toString();
        String donorDonationQuantity = request.getQuantity().toString();
        String remaining = (Integer.parseInt(requiredQuantity) - item.getReservedQuantity() )+"";



        emailService.donationInitiated(orpEmail,donorId,donorName,donorPhone,modeOfDonation,donationId,needItemId,needItemName,expectedDonationDate,expectedDeliveryDate,remainingQuantity+"",donorDonationQuantity,remaining);

        return new DonationResponse("Donation request created successfully", donationRequestId);
}
}