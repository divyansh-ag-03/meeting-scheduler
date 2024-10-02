package com.example.meeting_scheduler.mapper.impl;

import com.example.meeting_scheduler.dto.UserDTO;
import com.example.meeting_scheduler.mapper.UserMapper;
import com.example.meeting_scheduler.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());

        return userDTO;
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());

        return user;
    }
}
