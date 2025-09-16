package com.example.demo.controller;

import com.example.demo.controller.request.UserMovieRatingRequest;
import com.example.demo.controller.response.UserMovieRatingDto;
import com.example.demo.service.dto.MovieRecommendationDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/user/movie")
public interface UserMovieRatingController {

    @GetMapping("/genre")
    List<MovieRecommendationDto> getRecommendationsByGenre(
            @RequestParam Long userId,
            @RequestParam String genre,
            @RequestParam int count);

    @GetMapping("/emotion")
    List<MovieRecommendationDto> getRecommendationsByEmotion(
            @RequestParam Long userId,
            @RequestParam String emotion,
            @RequestParam int count);

    @PostMapping("/rating")
    UserMovieRatingDto rateMovie(@RequestBody UserMovieRatingRequest request);

    @GetMapping("/history/{userId}")
    List<UserMovieRatingDto> getUserRecommendationHistory(@PathVariable Long userId);
}