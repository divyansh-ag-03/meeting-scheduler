package com.example.meeting_scheduler.service;

import com.example.meeting_scheduler.dto.UserDTO;
import com.example.meeting_scheduler.entity.User;

import java.util.Optional;

public interface UserService {

    UserDTO verifyUser(String email);

    UserDTO toDTO(User user);

    User toEntity(UserDTO userDTO);

    User getUserByEmail(String email);
}
