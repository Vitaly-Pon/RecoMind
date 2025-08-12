package com.example.demo.controller;

import com.example.demo.controller.dto.request.MovieRecByEmotionRequest;
import com.example.demo.controller.dto.request.MovieRecommendationRequest;
import com.example.demo.controller.dto.response.MovieRecommendationsResponse;
import com.example.demo.service.MovieRecommendationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@AllArgsConstructor
public class MovieRecommendationControllerImp implements MovieRecommendationController {
    private final MovieRecommendationService movieRecommendationService;

    @Override
    public MovieRecommendationsResponse getRecommendMoviesInGenre(@Valid @RequestBody MovieRecommendationRequest request) {
        return movieRecommendationService.getMovieRecommendations(request.getGenre(), request.getCountOrDefault());
    }

    @Override
    public MovieRecommendationsResponse getRecMoviesBasedOnEmotion(@Valid @RequestBody MovieRecByEmotionRequest request) {
        return movieRecommendationService.getMovieRecommendationsOnEmotion(request.getEmotion(), request.getCountOrDefault());
    }
}
