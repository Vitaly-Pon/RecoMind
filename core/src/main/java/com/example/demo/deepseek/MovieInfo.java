package com.example.demo.deepseek;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieInfo {
    @JsonProperty("title")
    private String title;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("director")
    private String director;

    @JsonProperty("imdb_rating")
    private Double imdbRating;

    @JsonProperty("kinopoisk_rating")
    private Double kinopoiskRating;

    @JsonProperty("description")
    private String description;
}
