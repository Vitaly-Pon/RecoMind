package com.example.demo.service;

import com.example.demo.repository.entity.MovieRating;
import com.example.demo.service.dto.MovieRecommendationDto;

import java.math.BigDecimal;
import java.util.List;


public interface UserMovieRatingService {
    MovieRating rateMovie(Long userId, String title, BigDecimal rating);
    List<MovieRating> getUserRatings(Long userId);
    List<MovieRecommendationDto> getFilteredByGenre(Long userId, String genre, int count);
    List<MovieRecommendationDto> getFilteredByEmotion(Long userId, String emotion, int count);
}
