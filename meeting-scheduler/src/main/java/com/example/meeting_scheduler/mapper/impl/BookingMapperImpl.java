package com.example.meeting_scheduler.mapper.impl;

import com.example.meeting_scheduler.dto.*;
import com.example.meeting_scheduler.mapper.BookingMapper;
import com.example.meeting_scheduler.entity.Booking;
import com.example.meeting_scheduler.service.RoomService;
import com.example.meeting_scheduler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BookingMapperImpl implements BookingMapper {

    private final RoomService roomService;
    private final UserService userService;

    @Autowired
    public BookingMapperImpl(RoomService roomService, UserService userService) {
        this.roomService = roomService;
        this.userService = userService;
    }

    @Override
    public BookingDTO toDTO(Booking booking) {
        if (booking == null) {
            return null;
        }

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setRoomName(roomService.toDTO(booking.getRoom()));
        bookingDTO.setUserEmail(userService.toDTO(booking.getUser()));
        bookingDTO.setStartTime(booking.getStartTime());
        bookingDTO.setEndTime(booking.getEndTime());

        return bookingDTO;
    }

    @Override
    public BookingsByUserEmailDTO toBookingWithoutUserDTO(Booking booking) {
        if (booking == null) {
            return null;
        }

        BookingsByUserEmailDTO bookingsByUserEmailDTO = new BookingsByUserEmailDTO();
        bookingsByUserEmailDTO.setId(booking.getId());
        bookingsByUserEmailDTO.setRoomName(roomService.toDTO(booking.getRoom()));
        bookingsByUserEmailDTO.setStartTime(booking.getStartTime());
        bookingsByUserEmailDTO.setEndTime(booking.getEndTime());

        return bookingsByUserEmailDTO;
    }

    @Override
    public BookingsByRoomNameDTO toBookingWithoutRoomDTO(Booking booking) {
        if (booking == null) {
            return null;
        }

        BookingsByRoomNameDTO bookingsByRoomNameDTO = new BookingsByRoomNameDTO();
        bookingsByRoomNameDTO.setId(booking.getId());
        bookingsByRoomNameDTO.setUserEmail(userService.toDTO(booking.getUser()));
        bookingsByRoomNameDTO.setStartTime(booking.getStartTime());
        bookingsByRoomNameDTO.setEndTime(booking.getEndTime());

        return bookingsByRoomNameDTO;
    }

    @Override
    public Booking toEntity(BookingRequestDTO bookingRequestDTO) {
        if (bookingRequestDTO == null) {
            return null;
        }

        Booking booking = new Booking();
        booking.setRoom(roomService.getRoomByName(bookingRequestDTO.getRoomName()));
        booking.setUser(userService.getUserByEmail(bookingRequestDTO.getUserEmail()));
        booking.setStartTime(LocalDateTime.parse(bookingRequestDTO.getStartTime()));
        booking.setEndTime(LocalDateTime.parse(bookingRequestDTO.getEndTime()));

        return booking;
    }

    @Override
    public Booking updateEntity(Booking existingBooking, UpdateBookingRequestDTO updateBookingRequestDTO) {
        if (existingBooking == null || updateBookingRequestDTO == null) {
            return null;
        }

        existingBooking.setRoom(roomService.getRoomByName(updateBookingRequestDTO.getRoomName()));
        existingBooking.setStartTime(LocalDateTime.parse(updateBookingRequestDTO.getStartTime()));
        existingBooking.setEndTime(LocalDateTime.parse(updateBookingRequestDTO.getEndTime()));

        return existingBooking;
    }
}
