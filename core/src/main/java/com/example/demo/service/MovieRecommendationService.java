package com.example.demo.service;


import com.example.demo.service.dto.MovieRecommendationDto;

import java.util.List;

public interface MovieRecommendationService {
    List<MovieRecommendationDto> getMovieRecommendations(String genre, int count);
    List<MovieRecommendationDto> getMovieRecommendationsOnEmotion(String emotion, int count);
}
