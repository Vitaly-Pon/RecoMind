package com.example.demo.controller.dto.response;

import com.example.demo.entity.MovieRecommendationModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MovieRecommendationResponse {
    private List<MovieRecommendationDto> movies;

    public static MovieRecommendationResponse fromDtoList(List<MovieRecommendationDto> dtoMovies) {
        return new MovieRecommendationResponse(dtoMovies);
    }
}
