package com.example.meeting_scheduler.service.impl;

import com.example.meeting_scheduler.dto.UserDTO;
import com.example.meeting_scheduler.entity.User;
import com.example.meeting_scheduler.exception.ResourceNotFoundException;
import com.example.meeting_scheduler.mapper.UserMapper;
import com.example.meeting_scheduler.repository.UserRepository;
import com.example.meeting_scheduler.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO verifyUser(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return userMapper.toDTO(user);
        } else {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }

    }

    @Override
    public UserDTO toDTO(User user) {
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

    @Override
    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }
}
