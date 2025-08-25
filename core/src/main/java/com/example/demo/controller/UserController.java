package com.example.demo.controller;

import com.example.demo.dto.UserRegistrationRequest;
import com.example.demo.dto.UserResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserController {
    @PostMapping("/register")
    UserResponseDto register(@Valid @RequestBody UserRegistrationRequest request);

    @GetMapping
    List<UserResponseDto> getAllUsers();

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteUser(@PathVariable Long id);
}
