package com.example.demo.service;


public interface MovieRecommendationService {
    Object getMovieRecommendations(String genre, int count);
    Object getMovieRecommendationsOnEmotion(String emotion, int count);
}
