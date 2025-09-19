package com.example.demo.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class MovieRecommendationDto {
    private String title;
    private Integer year;
    private String director;
    private Double imdbRating;
    private Double kinopoiskRating;
    private String description;
}
