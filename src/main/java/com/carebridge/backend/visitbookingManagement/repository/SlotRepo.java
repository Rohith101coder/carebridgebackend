package com.carebridge.backend.visitbookingManagement.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.carebridge.backend.visitbookingManagement.entity.Slot;
import com.carebridge.backend.visitbookingManagement.enums.SlotStatus;

import jakarta.persistence.LockModeType;

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


List<Slot> findByOrphanageCareBridgeId(String orphanageCareBridgeId);

List<Slot> findByOrphanageCareBridgeIdAndSlotStatus(String orphanageCareBridgeId, SlotStatus status);


@Query("""
        SELECT s
        FROM Slot s

        WHERE
        s.slotStatus = 'AVAILABLE'

        AND
        (
        s.date < :today

        OR

        (
        s.date = :today
        AND
        s.endTime < :currentTime
        )
        )
        """)

    List<Slot> findExpiredSlots(
        @Param("today")
        LocalDate today,

        @Param("currentTime")
        LocalTime currentTime
    );



//     @Lock(LockModeType.PESSIMISTIC_WRITE)

// @Query("""
//     SELECT s
//     FROM Slot s
//     WHERE s.slotId = :slotId
// """)
// Optional<Slot> findBySlotIdForUpdate(
//         @Param("slotId")
//         String slotId
// );

Optional<Slot>  findBySlotId(String slotId);

// Optional<Slot> findBySlotId(String slotId);

}
