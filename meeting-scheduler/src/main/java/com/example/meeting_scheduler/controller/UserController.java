package com.example.meeting_scheduler.controller;

import com.example.meeting_scheduler.dto.UserDTO;
import com.example.meeting_scheduler.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/verify")
    public ResponseEntity<UserDTO> verifyUser(@RequestParam String email) {
        UserDTO userDTO = userService.verifyUser(email);
        return ResponseEntity.ok(userDTO);
    }
}
