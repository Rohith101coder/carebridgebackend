package com.carebridge.backend.visitbookingManagement.repository;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.carebridge.backend.visitbookingManagement.entity.Slot;

public interface SlotRepo extends JpaRepository<Slot, Long>{
   


    @Query("""
    SELECT
    CASE WHEN COUNT(s) > 0
    THEN true
    ELSE false
    END

    FROM Slot s

    WHERE
    s.orphanageCareBridgeId = :orpId

    AND
    s.date = :date

    AND
    s.slotStatus != 'CANCELLED'

    AND
    :startTime < s.endTime

    AND
    :endTime > s.startTime
""")
boolean existsOverlappingSlot(

        @Param("orpId")
        String orphanageCareBridgeId,

        @Param("date")
        LocalDate date,

        @Param("startTime")
        LocalTime startTime,

        @Param("endTime")
        LocalTime endTime
);
}
