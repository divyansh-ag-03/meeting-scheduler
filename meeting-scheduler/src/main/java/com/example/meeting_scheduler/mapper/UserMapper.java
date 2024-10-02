package com.example.meeting_scheduler.mapper;

import com.example.meeting_scheduler.dto.UserDTO;
import com.example.meeting_scheduler.entity.User;

public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
}
