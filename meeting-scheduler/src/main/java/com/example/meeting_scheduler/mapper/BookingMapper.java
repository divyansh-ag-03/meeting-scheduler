package com.example.meeting_scheduler.mapper;

import com.example.meeting_scheduler.dto.*;
import com.example.meeting_scheduler.entity.Booking;

public interface BookingMapper {
    BookingDTO toDTO(Booking booking);
    BookingsByUserEmailDTO toBookingWithoutUserDTO(Booking booking);
    BookingsByRoomNameDTO toBookingWithoutRoomDTO(Booking booking);
    Booking toEntity(BookingRequestDTO bookingRequestDTO);
    Booking updateEntity(Booking existingBooking, UpdateBookingRequestDTO updateBookingRequestDTO);
}
