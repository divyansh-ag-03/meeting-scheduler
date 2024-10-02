package com.example.meeting_scheduler.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {
    private String userEmail;
    private String roomName;
    private String startTime;
    private String endTime;
}
