package com.carebridge.backend.visitbookingManagement.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;
// import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.carebridge.backend.authManagement.entity.User;
import com.carebridge.backend.authManagement.exception.UserNotFoundException;
import com.carebridge.backend.authManagement.repository.UserRepository;
import com.carebridge.backend.needsManagement.exception.CommonException;
import com.carebridge.backend.orphanageManagement.entity.OrphanageProfile;
import com.carebridge.backend.orphanageManagement.exception.OrphanageProfileNotFoundException;
import com.carebridge.backend.orphanageManagement.repository.OrphanageProfileRepository;
import com.carebridge.backend.visitbookingManagement.dto.SlotRequest;
import com.carebridge.backend.visitbookingManagement.dto.SlotResponse;
import com.carebridge.backend.visitbookingManagement.entity.Slot;
import com.carebridge.backend.visitbookingManagement.enums.SlotStatus;
import com.carebridge.backend.visitbookingManagement.repository.SlotRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SlotService {
    
    private final SlotRepo slotRepo;
    private final UserRepository userRepository;
    private final OrphanageProfileRepository orphanageProfileRepository;

    @Transactional
public SlotResponse createSlot(
        SlotRequest request
){

    

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


    if(request.getStartTime()
            .isAfter(request.getEndTime())){

        throw new CommonException(
                "Start time must be before end time"
        );
    }

    if(request.getDate().equals(LocalDate.now())&&request.getStartTime().isBefore(LocalTime.now())){
        throw new CommonException("cannot create for past time");
    }


    if(request.getDate()
            .isBefore(LocalDate.now())){

        throw new CommonException(
                "Cannot create slot for past date"
        );
    }

   
    boolean slotExists =
            slotRepo
                    .existsOverlappingSlot(

                            orphanage.getCarebridgeId(),

                            request.getDate(),

                            request.getStartTime(),

                            request.getEndTime()
                    );

    if(slotExists){

        throw new CommonException(
                "Slot already exists in this time range"
        );
    }


    String slotId =
            "CB-SLOT-"
            +
            UUID.randomUUID()
                    .toString()
                    .substring(0, 8)
                    .toUpperCase();


    Slot slot = new Slot();

    slot.setSlotId(slotId);

    slot.setOrphanageCareBridgeId(
            orphanage.getCarebridgeId()
    );

    slot.setDate(
            request.getDate()
    );

    slot.setStartTime(
            request.getStartTime()
    );

    slot.setEndTime(
            request.getEndTime()
    );

    slot.setMaxVisitors(
            request.getMaxVisitors()
    );

    slot.setBookedCount(0);

    slot.setSlotStatus(
            SlotStatus.AVAILABLE
    );

    slot.setTitle(
            request.getTitle()
    );

    slot.setDescription(
            request.getDescription()
    );


    slotRepo.save(slot);

    return new SlotResponse(
            "Slot created successfully",
            slotId
    );
}


        public List<Slot> getAllSlots(){

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

        List<Slot> slots = slotRepo.findByOrphanageCareBridgeId(orphanage.getCarebridgeId());
                

                return slots;
                
        }


        
}
