package com.example.meeting_scheduler.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BookingDTO {
    private Long id;
    private RoomDTO roomName;
    private UserDTO userEmail;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
