package com.example.demo.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class UserMovieRatingDto {
    private Long id;
    private String movieTitle;
    private String director;
    private Integer year;
    private String genre;
    private String emotion;
    private Integer rating;
    private LocalDateTime createdAt;
}
