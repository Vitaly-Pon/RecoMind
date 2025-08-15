package com.example.demo.service;

import com.example.demo.controller.dto.response.MovieRecommendationsResponse;

public interface MovieRecommendationService {
    MovieRecommendationsResponse getMovieRecommendations(String genre, int count);
    MovieRecommendationsResponse getMovieRecommendationsOnEmotion(String emotion, int count);

}
