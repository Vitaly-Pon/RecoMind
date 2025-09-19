package com.example.demo.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MovieRecommendationDto {
    private String title;
    private Integer year;
    private String director;
    private Double imdbRating;
    private Double kinopoiskRating;
    private String description;
}
