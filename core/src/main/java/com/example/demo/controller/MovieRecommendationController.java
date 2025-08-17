package com.example.demo.controller;

import com.example.demo.controller.dto.request.MovieRecByEmotionRequest;
import com.example.demo.controller.dto.request.MovieRecommendationRequest;
import com.example.demo.controller.dto.response.MovieRecommendationsResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/movies")
public interface MovieRecommendationController {

    @PostMapping("/genre")
    Object getRecommendMoviesInGenre(@Valid @RequestBody MovieRecommendationRequest request,
                                     @RequestParam(defaultValue = "json") String format);

    @PostMapping("/emotion")
    Object getRecMoviesBasedOnEmotion(@Valid @RequestBody MovieRecByEmotionRequest request,
                                      @RequestParam(defaultValue = "json") String format);
}

