package com.example.demo.controller;

import com.example.demo.controller.dto.request.MovieRecByEmotionRequest;
import com.example.demo.controller.dto.request.MovieRecommendationRequest;
import com.example.demo.controller.dto.response.MovieRecommendationsResponse;
import com.example.demo.service.MovieRecommendationService;
import com.example.demo.util.MovieFormatter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MovieRecommendationControllerImp implements MovieRecommendationController {

    private final MovieRecommendationService movieRecommendationService;
    private final MovieFormatter movieFormatter;

    @Override
    public Object getRecommendMoviesInGenre(@Valid MovieRecommendationRequest request,
                                            @RequestParam(defaultValue = "json") String format) {
        MovieRecommendationsResponse response =
                movieRecommendationService.getMovieRecommendations(request.getGenre(), request.getCount());

        if ("text".equalsIgnoreCase(format)) {
            return String.join("\n", movieFormatter.formatMovies(response.getMovie()));
        }
        return response;
    }

    @Override
    public Object getRecMoviesBasedOnEmotion(@Valid MovieRecByEmotionRequest request,
                                             @RequestParam(defaultValue = "json") String format) {
        MovieRecommendationsResponse response =
                movieRecommendationService.getMovieRecommendationsOnEmotion(request.getEmotion(), request.getCount());

        if ("text".equalsIgnoreCase(format)) {
            return String.join("\n", movieFormatter.formatMovies(response.getMovie()));
        }
        return response;
    }
}