package com.example.demo.controller;

import com.example.demo.controller.request.MovieRecommendationRequest;
import com.example.demo.controller.request.UserMovieRatingRequest;
import com.example.demo.controller.response.MovieRecommendationResponse;
import com.example.demo.controller.response.UserMovieRatingHistoryResponse;
import com.example.demo.controller.response.UserMovieRatingResponse;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/movie")
public interface MovieRatingController {

    @PostMapping("/genre/recommendations")
    MovieRecommendationResponse getRecommendationsByGenre(
            @Valid @RequestBody MovieRecommendationRequest request); //TODO это выглядит как дубль, убрать?

    @PostMapping("/emotion/recommendations")
    MovieRecommendationResponse getRecommendationsByEmotion(
            @Valid @RequestBody MovieRecommendationRequest request);//TODO это выглядит как дубль, убрать?


    @PostMapping("/ratings")
    UserMovieRatingResponse rateMovie(@RequestBody UserMovieRatingRequest request);

    @GetMapping("/users/{userId}/ratings")
    UserMovieRatingHistoryResponse getUserRecommendationHistory(@PathVariable Long userId);//TODO вынести в другой контроллера
}