package com.example.demo.controller.dto.response;

import com.example.demo.entity.MovieRecommendationModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MovieRecommendationResponse {
    private List<MovieRecommendationModel> movies;

    public static MovieRecommendationResponse fromDomainList(List<MovieRecommendationModel> domainMovies) {
        return new MovieRecommendationResponse(domainMovies);
    }
}
