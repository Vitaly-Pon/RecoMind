package com.example.demo.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MovieRecommendationResponse {
    private List<MovieRecommendationDto> movies;
}
