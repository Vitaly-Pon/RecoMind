package com.example.demo.controller;

import com.example.demo.controller.dto.request.MovieRecByEmotionRequest;
import com.example.demo.controller.dto.request.MovieRecommendationRequest;
import com.example.demo.controller.dto.response.MovieRecommendationResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/movies")
public interface MovieRecommendationController {

    @PostMapping("/genre")
    MovieRecommendationResponse getRecommendMoviesInGenre(@Valid @RequestBody MovieRecommendationRequest request);

    @PostMapping("/emotion")
    MovieRecommendationResponse getRecMoviesBasedOnEmotion(@Valid @RequestBody MovieRecByEmotionRequest request);
}

