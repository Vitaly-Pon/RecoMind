package com.example.demo.controller;

import com.example.demo.controller.request.MovieRecommendationRequest;
import com.example.demo.controller.request.UserMovieRatingRequest;
import com.example.demo.controller.response.MovieRecommendationResponse;
import com.example.demo.controller.response.UserMovieRatingHistoryResponse;
import com.example.demo.controller.response.UserMovieRatingResponse;
import com.example.demo.mapper.MovieRecommendationRestMapper;
import com.example.demo.mapper.UserMovieRatingRestMapper;
import com.example.demo.repository.entity.UserMovieRating;
import com.example.demo.service.MovieRecommendationService;
import com.example.demo.service.UserMovieRatingService;
import com.example.demo.service.dto.MovieRecommendationDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user/movie")
public class UserMovieRatingControllerImp implements UserMovieRatingController {

    private final UserMovieRatingService userMovieRatingService;

    @Override
    public MovieRecommendationResponse getRecommendationsByGenre(@Valid @RequestBody MovieRecommendationRequest request) {
        List<MovieRecommendationDto> recommendations =
                userMovieRatingService.getFilteredByGenre(request.getUserId(), request.getGenre(), request.getCount());
        return new MovieRecommendationResponse(MovieRecommendationRestMapper.toDtoList(recommendations));
    }

    @Override
    public MovieRecommendationResponse getRecommendationsByEmotion(@Valid @RequestBody MovieRecommendationRequest request) {
        List<MovieRecommendationDto> recommendations =
                userMovieRatingService.getFilteredByEmotion(request.getUserId(), request.getEmotion(), request.getCount());
        return new MovieRecommendationResponse(MovieRecommendationRestMapper.toDtoList(recommendations));
    }

    @Override
    public UserMovieRatingResponse rateMovie(@Valid @RequestBody UserMovieRatingRequest request) {
        UserMovieRating saved = userMovieRatingService.rateMovieEntity(
                request.getUserId(),
                request.getGenre(),
                request.getEmotion(),
                request.getTitle(),
                request.getRating()
        );
        return new UserMovieRatingResponse(UserMovieRatingRestMapper.toDto(saved));
    }

    @Override
    public UserMovieRatingHistoryResponse getUserRecommendationHistory(@PathVariable Long userId) {
        List<UserMovieRating> ratings = userMovieRatingService.getUserRatings(userId);
        return new UserMovieRatingHistoryResponse(UserMovieRatingRestMapper.toDtoList(ratings));
    }
}