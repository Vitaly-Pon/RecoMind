package com.example.demo.controller;

import com.example.demo.controller.request.MovieRecommendationRequest;
import com.example.demo.controller.response.MovieRecommendationResponse;
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

    @Override
    public MovieRecommendationResponse getRecommendMoviesInGenre(@Valid @RequestBody MovieRecommendationRequest request) {
        return new MovieRecommendationResponse(
                MovieRecommendationRestMapper.toDtoList(
                        service.getMovieRecommendations(request.getGenre(), request.getCount())
                )
        );
    }

    @Override
    public MovieRecommendationResponse getRecMoviesBasedOnEmotion(@Valid @RequestBody MovieRecommendationRequest request) {
        return new MovieRecommendationResponse(
                MovieRecommendationRestMapper.toDtoList(
                        service.getMovieRecommendationsOnEmotion(request.getEmotion(), request.getCount())
                )
        );
    }
}