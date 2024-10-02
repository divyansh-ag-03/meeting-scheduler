package com.example.meeting_scheduler.controller;

import com.example.meeting_scheduler.dto.*;
import com.example.meeting_scheduler.service.BookingService;
import com.example.meeting_scheduler.exception.ResourceNotFoundException;
import com.example.meeting_scheduler.exception.RoomAlreadyBookedException;
import com.example.meeting_scheduler.exception.ErrorResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public ResponseEntity<BookingDTO> bookRoom(@RequestBody BookingRequestDTO bookingRequest) throws RoomAlreadyBookedException{
        BookingDTO booking = bookingService.bookRoom(bookingRequest);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<BookingsByUserEmailDTO>> getBookingsByEmail(@PathVariable String email) {
        List<BookingsByUserEmailDTO> bookings = bookingService.getBookingsByEmail(email);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/room/{roomName}")
    public ResponseEntity<List<BookingsByRoomNameDTO>> getBookingsByRoomName(@PathVariable String roomName) {
        List<BookingsByRoomNameDTO> bookings = bookingService.getBookingsByRoomName(roomName);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingsByUserEmailDTO> getBookingById(@PathVariable Long id) {
        BookingsByUserEmailDTO booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Long id,
                                                @RequestBody UpdateBookingRequestDTO updateRequest) {
        BookingDTO booking = bookingService.updateBooking(id, updateRequest);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookingDTO>> getAllBookedSlots() {
        List<BookingDTO> bookings = bookingService.getAllBookedSlots();
        return ResponseEntity.ok(bookings);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BookingDTO> deleteBookingById(@PathVariable Long id) {
        BookingDTO booking = bookingService.deleteBooking(id);
        return ResponseEntity.ok(booking);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}