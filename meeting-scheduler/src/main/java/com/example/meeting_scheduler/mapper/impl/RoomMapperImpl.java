package com.example.meeting_scheduler.mapper.impl;

import com.example.meeting_scheduler.dto.RoomDTO;
import com.example.meeting_scheduler.mapper.RoomMapper;
import com.example.meeting_scheduler.entity.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomMapperImpl implements RoomMapper {

    @Override
    public RoomDTO toDTO(Room room) {
        if (room == null) {
            return null;
        }

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setName(room.getName());

        return roomDTO;
    }

    @Override
    public Room toEntity(RoomDTO roomDTO) {
        if (roomDTO == null) {
            return null;
        }

        Room room = new Room();
        room.setId(roomDTO.getId());
        room.setName(roomDTO.getName());

        return room;
    }
}
