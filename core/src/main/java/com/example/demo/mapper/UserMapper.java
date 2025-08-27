package com.example.demo.mapper;

import com.example.demo.controller.response.UserResponse;
import com.example.demo.repository.entity.User;

public class UserMapper {
    public static UserResponse toDto(User user){
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
