package com.example.demo.controller;


import com.example.demo.controller.request.MovieRecommendationRequest;
import com.example.demo.controller.response.MovieRecommendationResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/movie")
public interface MovieRecommendationController {

    @PostMapping("/genre/recommendations")
    MovieRecommendationResponse getRecommendMoviesInGenre(
            @Valid @RequestBody MovieRecommendationRequest request);

    @PostMapping("/emotion/recommendations")
    MovieRecommendationResponse getRecMoviesBasedOnEmotion(
            @Valid @RequestBody MovieRecommendationRequest request);

}