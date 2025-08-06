package com.example.demo.controller;

import com.example.demo.controller.dto.response.MovieRecommendationsResponse;
import com.example.demo.servise.MovieRecommendationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
public class MovieRecommendationController {
    private final MovieRecommendationService movieRecommendationService;

    public MovieRecommendationController(MovieRecommendationService movieRecommendationService) {
        this.movieRecommendationService = movieRecommendationService;
    }

    @PostMapping("/recommend")
    public MovieRecommendationsResponse getRecommendMovies(@RequestParam String genre, @RequestParam(defaultValue = "5") int count) {
        return movieRecommendationService.getMovieRecommendations(genre, count);
    }
}
