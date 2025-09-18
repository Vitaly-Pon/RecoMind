package com.example.demo.controller;

import com.example.demo.controller.request.MovieRecommendationRequest;
import com.example.demo.controller.response.MovieRecommendationResponse;
import com.example.demo.mapper.MovieRecommendationRestMapper;
import com.example.demo.service.MovieRecommendationService;
import com.example.demo.service.UserMovieRatingServiceImp;
import com.example.demo.service.dto.MovieRecommendationDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class MovieRecommendationControllerImp implements MovieRecommendationController {

    private final MovieRecommendationService service;
    private final UserMovieRatingServiceImp userMovieRatingServiceImp;

    @Override
    public MovieRecommendationResponse getRecommendMoviesInGenre(@Valid @RequestBody MovieRecommendationRequest request) {
        if (request.getUserId() != null){
            List<MovieRecommendationDto> recommendations =
                    userMovieRatingServiceImp.getFilteredByGenre(request.getUserId(), request.getGenre(), request.getCount());
            return new MovieRecommendationResponse(MovieRecommendationRestMapper.toDtoList(recommendations));
        } else {
            return new MovieRecommendationResponse(
                MovieRecommendationRestMapper.toDtoList(
                        service.getMovieRecommendations(request.getGenre(), request.getCount())
                )
        );
        }
    }

    @Override
    public MovieRecommendationResponse getRecMoviesBasedOnEmotion(@Valid @RequestBody MovieRecommendationRequest request)
    {
        if (request.getUserId() != null){
            List<MovieRecommendationDto> recommendations =
                    userMovieRatingServiceImp.getFilteredByEmotion(request.getUserId(), request.getEmotion(), request.getCount());
            return new MovieRecommendationResponse(MovieRecommendationRestMapper.toDtoList(recommendations));
        } else {return new MovieRecommendationResponse(
                MovieRecommendationRestMapper.toDtoList(
                        service.getMovieRecommendationsOnEmotion(request.getEmotion(), request.getCount())
                )
        );
        }
    }
}