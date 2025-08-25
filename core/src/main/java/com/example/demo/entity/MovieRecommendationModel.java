package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
// Доменная модель независимая от JSON и API.
@Data
@AllArgsConstructor
public class MovieRecommendationModel {
    private String title;
    private Integer year;
    private String director;
    private Double imdbRating;
    private Double kinopoiskRating;
    private String description;

}
