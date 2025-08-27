package com.example.demo.service;

import com.example.demo.controller.request.UserRegistrationRequest;
import com.example.demo.controller.response.UserResponse;
import com.example.demo.repository.entity.User;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp  implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // внедрится бин

    public UserResponse register(UserRegistrationRequest request) {
        // существует ли уже username
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Имя пользователя уже занято");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword())) // тут хэширование
                .build();

        User savedUser = userRepository.save(user);
        return UserMapper.toDto(savedUser);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .toList();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
