package com.example.meeting_scheduler.service;

import com.example.meeting_scheduler.dto.*;
import com.example.meeting_scheduler.exception.RoomAlreadyBookedException;

import java.util.List;

public interface BookingService {

    BookingDTO bookRoom(BookingRequestDTO bookingRequest) throws RoomAlreadyBookedException;

    List<BookingsByUserEmailDTO> getBookingsByEmail(String email);

    BookingDTO updateBooking(Long bookingId, UpdateBookingRequestDTO updateRequest);

    List<BookingDTO> getAllBookedSlots();

    BookingDTO deleteBooking(Long bookingId);

    List<BookingsByRoomNameDTO> getBookingsByRoomName(String roomName);

    BookingsByUserEmailDTO getBookingById(Long id);
}
