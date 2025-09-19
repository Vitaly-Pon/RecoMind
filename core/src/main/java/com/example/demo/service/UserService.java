package com.example.demo.service;

import com.example.demo.controller.request.UserRegistrationRequest;
import com.example.demo.controller.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse register(UserRegistrationRequest request);
    List<UserResponse> getAllUsers();
    void deleteUser(Long id);
}
