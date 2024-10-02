package com.example.meeting_scheduler.service.impl;

import com.example.meeting_scheduler.dto.RoomDTO;
import com.example.meeting_scheduler.entity.Room;
import com.example.meeting_scheduler.exception.ResourceNotFoundException;
import com.example.meeting_scheduler.mapper.RoomMapper;
import com.example.meeting_scheduler.repository.RoomRepository;
import com.example.meeting_scheduler.service.RoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
    }

    @Override
    public List<RoomDTO> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoomDTO addRoom(String name) {
        Room room = new Room();
        room.setName(name);
        room = roomRepository.save(room);
        return toDTO(room);
    }

    @Override
    public RoomDTO updateRoom(Long id, Map<String, Object> roomDetails) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
        for (Map.Entry<String, Object> entry : roomDetails.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            Field field = ReflectionUtils.findField(Room.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, room, value);
            }
        }
        room = roomRepository.save(room);
        return toDTO(room);
    }

    @Override
    public RoomDTO deleteRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));

        RoomDTO deletedRoom = roomMapper.toDTO(room);
        roomRepository.delete(room);
        return deletedRoom;
    }

    @Override
    public Room getRoomByName(String name) {
        return roomRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with name: " + name));
    }

    @Override
    public RoomDTO toDTO(Room room) {
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
