package com.example.meeting_scheduler.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.meeting_scheduler.entity.Booking;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserEmail(String email);
    List<Booking> findByRoomName(String roomName);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.room.id = :roomId " +
            "AND ((b.startTime < :endTime AND b.endTime > :startTime) " +
            "OR (b.startTime >= :startTime AND b.startTime < :endTime) " +
            "OR (b.endTime > :startTime AND b.endTime <= :endTime))")
    List<Booking> findOverlapBookings(@Param("roomId") Long roomId,
                                      @Param("startTime") LocalDateTime startTime,
                                      @Param("endTime") LocalDateTime endTime);
}