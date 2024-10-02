package com.example.meeting_scheduler.controller;

import com.example.meeting_scheduler.dto.RoomDTO;
import com.example.meeting_scheduler.service.RoomService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    public RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        List<RoomDTO> rooms = roomService.getAllRooms();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<RoomDTO> addRoom(@RequestBody @Valid RoomDTO roomDTO) {
        RoomDTO addedRoom = roomService.addRoom(roomDTO.getName());
        return new ResponseEntity<>(addedRoom, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id, @RequestBody Map<String, Object> roomDetails) {
        RoomDTO updatedRoomDTO = roomService.updateRoom(id, roomDetails);
        return ResponseEntity.ok(updatedRoomDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RoomDTO> deleteRoom(@PathVariable Long id) {
        RoomDTO roomDTO = roomService.deleteRoom(id);
        return ResponseEntity.ok(roomDTO);
    }
}
