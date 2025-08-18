package com.example.demo.controller;

import com.example.demo.controller.dto.request.MovieRecByEmotionRequest;
import com.example.demo.controller.dto.request.MovieRecommendationRequest;
import com.example.demo.service.MovieRecommendationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/movies")
public class MovieRecommendationControllerImp {

    private final MovieRecommendationService service;

    @PostMapping("/genre")
    public Object recommendByGenre(@Valid @RequestBody MovieRecommendationRequest request,
                                   @RequestParam(defaultValue = "json") String format) {
        return service.getMovieRecommendations(request.getGenre(), request.getCount(), format);
    }

    @PostMapping("/emotion")
    public Object recommendByEmotion(@Valid @RequestBody MovieRecByEmotionRequest request,
                                     @RequestParam(defaultValue = "json") String format) {
        return service.getMovieRecommendationsOnEmotion(request.getEmotion(), request.getCount(), format);
    }
}