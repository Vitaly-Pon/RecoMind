package com.example.demo.controller;

import com.example.demo.controller.dto.request.MovieRecByEmotionRequest;
import com.example.demo.controller.dto.request.MovieRecommendationRequest;
import com.example.demo.controller.dto.response.MovieRecommendationsResponse;
import com.example.demo.service.MovieRecommendationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MovieRecommendationControllerImp implements MovieRecommendationController{
    private final MovieRecommendationService service;

    public MovieRecommendationsResponse getRecommendMoviesInGenre(@Valid @RequestBody MovieRecommendationRequest request) {
        return service.getMovieRecommendations(request.getGenre(), request.getCount());
    }

    public MovieRecommendationsResponse getRecMoviesBasedOnEmotion(@Valid @RequestBody MovieRecByEmotionRequest request) {
        return service.getMovieRecommendationsOnEmotion(request.getEmotion(), request.getCount());
    }
}