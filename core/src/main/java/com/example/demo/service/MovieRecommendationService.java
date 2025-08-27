package com.example.demo.service;


import com.example.demo.controller.dto.response.MovieRecommendationResponse;
import com.example.demo.entity.MovieRecommendationModel;

import java.util.List;

public interface MovieRecommendationService {
    List<MovieRecommendationModel> getMovieRecommendations(String genre, int count);
    List<MovieRecommendationModel> getMovieRecommendationsOnEmotion(String emotion, int count);
}
