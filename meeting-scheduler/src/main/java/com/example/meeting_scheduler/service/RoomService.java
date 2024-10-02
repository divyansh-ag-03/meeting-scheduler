package com.example.meeting_scheduler.service;

import com.example.meeting_scheduler.dto.RoomDTO;
import com.example.meeting_scheduler.entity.Room;

import java.util.List;
import java.util.Map;

public interface RoomService {

    List<RoomDTO> getAllRooms();

    RoomDTO addRoom(String name);

    RoomDTO updateRoom(Long id, Map<String, Object> roomDetails);

    RoomDTO deleteRoom(Long id);

    RoomDTO toDTO(Room room);

    Room getRoomByName(String name);

    Room toEntity(RoomDTO roomDTO);
}
