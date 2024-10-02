package com.example.meeting_scheduler.mapper;

import com.example.meeting_scheduler.dto.RoomDTO;
import com.example.meeting_scheduler.entity.Room;

public interface RoomMapper {
    RoomDTO toDTO(Room room);
    Room toEntity(RoomDTO roomDTO);
}
