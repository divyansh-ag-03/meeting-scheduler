package com.example.meeting_scheduler.service.impl;

import com.example.meeting_scheduler.dto.*;
import com.example.meeting_scheduler.entity.Booking;
import com.example.meeting_scheduler.entity.Room;
import com.example.meeting_scheduler.entity.User;
import com.example.meeting_scheduler.exception.ResourceNotFoundException;
import com.example.meeting_scheduler.exception.RoomAlreadyBookedException;
import com.example.meeting_scheduler.mapper.BookingMapper;
import com.example.meeting_scheduler.repository.BookingRepository;
import com.example.meeting_scheduler.repository.RoomRepository;
import com.example.meeting_scheduler.repository.UserRepository;
import com.example.meeting_scheduler.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private  RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  BookingMapper bookingMapper;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, RoomRepository roomRepository,
                              UserRepository userRepository, BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.bookingMapper = bookingMapper;
    }

    @Override
    public BookingDTO bookRoom(BookingRequestDTO bookingRequest) throws RoomAlreadyBookedException {
        if (isRoomAlreadyBooked(bookingRequest.getRoomName(), LocalDateTime.parse(bookingRequest.getStartTime()), LocalDateTime.parse(bookingRequest.getEndTime()))) {
            /*throw new RoomAlreadyBookedException("Room is already booked for the specified time.");*/
            throw new RoomAlreadyBookedException(bookingRequest.getRoomName(), bookingRequest.getStartTime(), bookingRequest.getEndTime());
        }

        User user = userRepository.findByEmail(bookingRequest.getUserEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + bookingRequest.getUserEmail()));

        Room room = roomRepository.findByName(bookingRequest.getRoomName())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with name: " + bookingRequest.getRoomName()));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setStartTime(LocalDateTime.parse(bookingRequest.getStartTime()));
        booking.setEndTime(LocalDateTime.parse(bookingRequest.getEndTime()));

        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapper.toDTO(savedBooking);
    }

    @Override
    public List<BookingsByUserEmailDTO> getBookingsByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        List<Booking> bookings = bookingRepository.findByUserEmail(email);
        return bookings.stream()
                .map(bookingMapper::toBookingWithoutUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingsByRoomNameDTO> getBookingsByRoomName(String roomName) {
        Room room = roomRepository.findByName(roomName)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with name: " + roomName));

        List<Booking> bookings = bookingRepository.findByRoomName(roomName);
        return bookings.stream()
                .map(bookingMapper::toBookingWithoutRoomDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookingsByUserEmailDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        return bookingMapper.toBookingWithoutUserDTO(booking);
    }

    @Override
    public BookingDTO updateBooking(Long bookingId, UpdateBookingRequestDTO updateRequest){

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        if (updateRequest.getRoomName() != null) {
            Room room = roomRepository.findByName(updateRequest.getRoomName())
                    .orElseThrow(() -> new ResourceNotFoundException("Room not found with name: " + updateRequest.getRoomName()));
            booking.setRoom(room);
        }
        if (updateRequest.getStartTime() != null) {
            booking.setStartTime(LocalDateTime.parse(updateRequest.getStartTime()));
        }
        if (updateRequest.getEndTime() != null) {
            booking.setEndTime(LocalDateTime.parse(updateRequest.getEndTime()));
        }
        Booking updatedBooking = bookingRepository.save(booking);
        return bookingMapper.toDTO(updatedBooking);
    }

    @Override
    public List<BookingDTO> getAllBookedSlots() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(bookingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookingDTO deleteBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        BookingDTO deletedBooking = bookingMapper.toDTO(booking);
        bookingRepository.delete(booking);
        return deletedBooking;
    }

    private boolean isRoomAlreadyBooked(String roomName, LocalDateTime startTime, LocalDateTime endTime) {
        Room room = roomRepository.findByName(roomName)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with name: " + roomName));
        Long roomId = room.getId();

        List<Booking> existingBookings = bookingRepository.findOverlapBookings(roomId, startTime, endTime);

        for (Booking booking : existingBookings) {
            LocalDateTime bookingStartTime = booking.getStartTime();
            LocalDateTime bookingEndTime = booking.getEndTime();

            if (startTime.isBefore(bookingEndTime) && endTime.isAfter(bookingStartTime)) {
                throw new RoomAlreadyBookedException(roomName, bookingStartTime.toString(), bookingEndTime.toString());
            }
        }
        return false;
    }

}
