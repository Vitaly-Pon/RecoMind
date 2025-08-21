package com.example.demo.controller;

import com.example.demo.controller.dto.request.MovieRecByEmotionRequest;
import com.example.demo.controller.dto.request.MovieRecommendationRequest;
import com.example.demo.service.MovieRecommendationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MovieRecommendationControllerImp implements MovieRecommendationController{
// TODO удалить формат
    private final MovieRecommendationService service;

    public Object getRecommendMoviesInGenre(@Valid @RequestBody MovieRecommendationRequest request) {
        return service.getMovieRecommendations(request.getGenre(), request.getCount());
    }

    public Object getRecMoviesBasedOnEmotion(@Valid @RequestBody MovieRecByEmotionRequest request) {
        return service.getMovieRecommendationsOnEmotion(request.getEmotion(), request.getCount());
    }
}