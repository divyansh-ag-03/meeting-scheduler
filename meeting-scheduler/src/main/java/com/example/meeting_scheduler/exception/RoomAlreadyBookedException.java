package com.example.meeting_scheduler.exception;

import lombok.Data;

@Data
public class RoomAlreadyBookedException extends RuntimeException {
    private String roomName;
    private String startTime;
    private String endTime;
    public RoomAlreadyBookedException(String roomName, String startTime, String endTime) {
        super(String.format("Room %s is already booked from %s to %s", roomName, startTime, endTime));
        this.roomName = roomName;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
