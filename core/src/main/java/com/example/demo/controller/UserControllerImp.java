package com.example.demo.controller;

import com.example.demo.dto.UserRegistrationRequest;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.service.UserServiceImp;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserControllerImp implements UserController {
    private final UserServiceImp userServiceImp;

    @PostMapping("/register")
    public UserResponseDto register(@Valid @RequestBody UserRegistrationRequest request) {
        return userServiceImp.register(request);
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userServiceImp.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userServiceImp.deleteUser(id);
        return ResponseEntity.ok("Пользователь с id " + id + " успешно удалён");
    }
}
