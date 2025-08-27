package com.example.demo.controller;

import com.example.demo.controller.dto.request.MovieRecByEmotionRequest;
import com.example.demo.controller.dto.request.MovieRecommendationRequest;
import com.example.demo.controller.dto.response.MovieRecommendationResponse;
import com.example.demo.mapper.MovieRecommendationRestMapper;
import com.example.demo.service.MovieRecommendationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MovieRecommendationControllerImp implements MovieRecommendationController {
    private final MovieRecommendationService service;

    public MovieRecommendationResponse getRecommendMoviesInGenre(
            @Valid @RequestBody MovieRecommendationRequest request) {

        return new MovieRecommendationResponse(
                MovieRecommendationRestMapper.toDtoList(
                        service.getMovieRecommendations(request.getGenre(), request.getCount()))
        );
    }

    public MovieRecommendationResponse getRecMoviesBasedOnEmotion(
            @Valid @RequestBody MovieRecByEmotionRequest request) {

        return new MovieRecommendationResponse(
                MovieRecommendationRestMapper.toDtoList(
                        service.getMovieRecommendationsOnEmotion(request.getEmotion(), request.getCount()))
        );
    }
}