package com.example.demo.mapper;

import com.example.demo.dto.UserResponseDto;
import com.example.demo.entity.User;

public class UserMapper {
    public static UserResponseDto toDto(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
