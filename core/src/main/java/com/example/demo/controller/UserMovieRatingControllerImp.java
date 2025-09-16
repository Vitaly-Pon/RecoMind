package com.example.demo.controller;

import com.example.demo.controller.request.UserMovieRatingRequest;
import com.example.demo.controller.response.UserMovieRatingDto;
import com.example.demo.mapper.UserMovieRatingRestMapper;
import com.example.demo.repository.entity.UserMovieRating;
import com.example.demo.service.MovieRecommendationService;
import com.example.demo.service.UserMovieRatingService;
import com.example.demo.service.dto.MovieRecommendationDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user/movie")
public class UserMovieRatingControllerImp implements UserMovieRatingController {

    private final MovieRecommendationService movieRecommendationService;
    private final UserMovieRatingService userMovieRatingService;

    // === РЕКОМЕНДАЦИИ ПО ЖАНРУ ===
    @GetMapping("/genre")
    public List<MovieRecommendationDto> getRecommendationsByGenre(
            @RequestParam Long userId,
            @RequestParam String genre,
            @RequestParam int count
    ) {
        return userMovieRatingService.getFilteredByGenre(userId, genre, count);
    }

    // === РЕКОМЕНДАЦИИ ПО ЭМОЦИИ ===
    @GetMapping("/emotion")
    public List<MovieRecommendationDto> getRecommendationsByEmotion(
            @RequestParam Long userId,
            @RequestParam String emotion,
            @RequestParam int count
    ) {
        return userMovieRatingService.getFilteredByEmotion(userId, emotion, count);
    }

    // === СОХРАНЕНИЕ ОЦЕНКИ ===
    @PostMapping("/rating")
    public UserMovieRatingDto rateMovie(@RequestBody UserMovieRatingRequest request) {
        UserMovieRating saved = userMovieRatingService.rateMovieEntity(
                request.getUserId(),
                request.getGenre(),
                request.getEmotion(),
                request.getTitle(),
                request.getRating()
        );
        return UserMovieRatingRestMapper.toDto(saved);
    }

    // === ИСТОРИЯ ОЦЕНОК ===
    @GetMapping("/history/{userId}")
    public List<UserMovieRatingDto> getUserRecommendationHistory(@PathVariable Long userId) {
        List<UserMovieRating> ratings = userMovieRatingService.getUserRatings(userId);
        return UserMovieRatingRestMapper.toDtoList(ratings);
    }
}
