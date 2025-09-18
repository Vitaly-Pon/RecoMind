package com.example.demo.controller;

import com.example.demo.controller.request.UserRegistrationRequest;
import com.example.demo.controller.response.UserMovieRatingHistoryResponse;
import com.example.demo.controller.response.UserResponse;
import com.example.demo.mapper.UserMovieRatingRestMapper;
import com.example.demo.repository.entity.MovieRating;
import com.example.demo.service.UserMovieRatingService;
import com.example.demo.service.UserServiceImp;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserControllerImp implements UserController {
    private final UserServiceImp userServiceImp;
    private final UserMovieRatingService userMovieRatingService;

    @Override
    public UserResponse register(@Valid @RequestBody UserRegistrationRequest request) {
        return userServiceImp.register(request);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userServiceImp.getAllUsers();
    }

    @Override
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) { //TODO не возвращать объект(только код)
        userServiceImp.deleteUser(id);
        return ResponseEntity.noContent().build(); // 204
    }

    @Override
    public UserMovieRatingHistoryResponse getUserRecommendationHistory(@PathVariable Long userId) {
        List<MovieRating> ratings = userMovieRatingService.getUserRatings(userId);
        return new UserMovieRatingHistoryResponse(UserMovieRatingRestMapper.toDtoList(ratings));
    }
}
