package com.carebridge.backend.visitbookingManagement.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.carebridge.backend.visitbookingManagement.entity.Slot;
import com.carebridge.backend.visitbookingManagement.enums.SlotStatus;
import com.carebridge.backend.visitbookingManagement.repository.SlotRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SlotSchedulerService {
    
    private final SlotRepo slotRepo;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void expireSlots(){

        LocalDate today = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        List<Slot> expiredSlots = slotRepo.findExpiredSlots(today, currentTime);

         for(Slot slot : expiredSlots){

            slot.setSlotStatus(
                    SlotStatus.EXPIRED
            );
        }

         slotRepo.saveAll(expiredSlots);
    }
}
