package com.example.demo.controller;

import com.example.demo.controller.dto.request.MovieRecByEmotionRequest;
import com.example.demo.controller.dto.request.MovieRecommendationRequest;
import com.example.demo.controller.dto.response.MovieRecommendationsResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/movies")
public interface MovieRecommendationController {
    @PostMapping("/recommendInGenre")
    MovieRecommendationsResponse getRecommendMoviesInGenre(@Valid @RequestBody MovieRecommendationRequest request);

    @PostMapping("/recommendBasedOnEmotion")
    MovieRecommendationsResponse getRecMoviesBasedOnEmotion(@Valid @RequestBody MovieRecByEmotionRequest request);
}
