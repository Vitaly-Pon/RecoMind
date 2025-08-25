package com.example.demo.service;

import com.example.demo.dto.UserRegistrationRequest;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.entity.User;

import java.util.List;

public interface UserService {

    UserResponseDto register(UserRegistrationRequest request);
    List<UserResponseDto> getAllUsers();
    void deleteUser(Long id);
}
