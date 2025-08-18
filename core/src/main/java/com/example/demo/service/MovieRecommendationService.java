package com.example.demo.service;


public interface MovieRecommendationService {
    Object getMovieRecommendations(String genre, int count, String format);
    Object getMovieRecommendationsOnEmotion(String emotion, int count, String format);
}
