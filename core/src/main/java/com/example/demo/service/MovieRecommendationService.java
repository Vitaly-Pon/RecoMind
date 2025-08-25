package com.example.demo.service;


import com.example.demo.controller.dto.response.MovieRecommendationResponse;

public interface MovieRecommendationService {
    MovieRecommendationResponse getMovieRecommendations(String genre, int count);
    MovieRecommendationResponse getMovieRecommendationsOnEmotion(String emotion, int count);
}
