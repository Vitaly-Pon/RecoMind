package com.example.demo.controller;

import com.example.demo.controller.request.UserRegistrationRequest;
import com.example.demo.controller.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/user")
public interface UserController {
    @PostMapping("/register")
    UserResponse register(@Valid @RequestBody UserRegistrationRequest request);

    @GetMapping
    List<UserResponse> getAllUsers();

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteUser(@PathVariable Long id);
}
