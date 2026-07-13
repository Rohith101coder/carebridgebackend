package com.carebridge.backend.slotDetailsFetchManagment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.carebridge.backend.orphanageManagement.entity.OrphanageProfile;
import com.carebridge.backend.orphanageManagement.repository.OrphanageProfileRepository;
import com.carebridge.backend.slotDetailsFetchManagment.dto.OrpAndSlotsDetailsDTO;
import com.carebridge.backend.slotDetailsFetchManagment.dto.SlotDetailsDTO;
import com.carebridge.backend.visitbookingManagement.entity.Slot;
import com.carebridge.backend.visitbookingManagement.enums.SlotStatus;
import com.carebridge.backend.visitbookingManagement.repository.SlotRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FetchSlotsService {
    private final OrphanageProfileRepository orphanageProfileRepository;

    private final SlotRepo slotRepo;

    public List<OrpAndSlotsDetailsDTO> getDetails(){

        List<OrphanageProfile> orpProfiles = orphanageProfileRepository.findAll();

        List<OrpAndSlotsDetailsDTO> allData = new ArrayList<>();
        for(OrphanageProfile profile : orpProfiles){
            OrpAndSlotsDetailsDTO data = new OrpAndSlotsDetailsDTO();
            data.setOrphanageId(profile.getCarebridgeId());
            data.setOrphanageName(profile.getOrphanageName());
            data.setLocation(profile.getDistrict());
            data.setProfileImage(profile.getOrphanageProfilePic());
            List<Slot> slots = slotRepo.findByOrphanageCareBridgeIdAndSlotStatus(profile.getCarebridgeId(),SlotStatus.AVAILABLE);
            List<SlotDetailsDTO> slotList = new ArrayList<>();
            for(Slot slot : slots){
                SlotDetailsDTO slotDTO = new SlotDetailsDTO();
                slotDTO.setSlotId(slot.getSlotId());
                slotDTO.setDate(slot.getDate().toString());
                slotDTO.setStartTime(slot.getStartTime().toString());
                slotDTO.setEndTime(slot.getEndTime().toString());
                slotDTO.setCapacity(slot.getMaxVisitors().toString());
                slotDTO.setAvailableSeats((slot.getMaxVisitors() - slot.getBookedCount())+"");
                slotDTO.setIsBooked(slot.getSlotStatus().name());
                slotList.add(slotDTO);
            }
            data.setOrpSlots(slotList);
            allData.add(data);
        }
        


        return allData;
    }
}
