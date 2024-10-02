package com.example.meeting_scheduler.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BookingsByUserEmailDTO {
    private Long id;
    private RoomDTO roomName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
