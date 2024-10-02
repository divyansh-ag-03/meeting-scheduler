package com.example.meeting_scheduler.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookingRequestDTO {

    private String roomName;
    private String startTime;
    private String endTime;
}
