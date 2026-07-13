package com.carebridge.backend.slotDetailsFetchManagment.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrpAndSlotsDetailsDTO {
    
    private String orphanageId;

    private String orphanageName;

    private String location;

    private String profileImage;

    private List<SlotDetailsDTO> orpSlots;
}
