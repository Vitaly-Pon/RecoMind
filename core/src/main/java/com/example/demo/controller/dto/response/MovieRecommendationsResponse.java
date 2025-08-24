package com.example.demo.controller.dto.response;

import com.example.demo.domain.MovieRecommendationModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MovieRecommendationsResponse {
    private List<MovieRecommendationModel> movies;

    public static MovieRecommendationsResponse fromDomainList(List<MovieRecommendationModel> domainMovies) {
        return new MovieRecommendationsResponse(domainMovies);
    }
}
