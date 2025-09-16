package com.example.demo.controller.response;

import com.example.demo.service.dto.MovieRecommendationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class UserMovieRatingResponse {
    private List<MovieRecommendationDto> userMovies;
}
