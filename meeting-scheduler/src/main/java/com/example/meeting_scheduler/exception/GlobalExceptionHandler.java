package com.example.meeting_scheduler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoomAlreadyBookedException.class)
    public ResponseEntity<ErrorResponse> handleRoomAlreadyBooked(RoomAlreadyBookedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        errorResponse.addDetail("roomName", ex.getRoomName());
        errorResponse.addDetail("startTime", ex.getStartTime());
        errorResponse.addDetail("endTime", ex.getEndTime());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
