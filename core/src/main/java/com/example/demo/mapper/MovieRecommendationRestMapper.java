package com.example.demo.mapper;

import com.example.demo.controller.dto.response.MovieRecommendationDto;
import com.example.demo.entity.MovieRecommendationModel;

import java.util.List;

public class MovieRecommendationRestMapper {
    public static MovieRecommendationDto toDto(MovieRecommendationModel model) {
        return MovieRecommendationDto.builder()
                .title(model.getTitle())
                .year(model.getYear())
                .director(model.getDirector())
                .imdbRating(model.getImdbRating())
                .kinopoiskRating(model.getKinopoiskRating())
                .description(model.getDescription())
                .build();
    }

    public static List<MovieRecommendationDto> toDtoList(List<MovieRecommendationModel> models) {
        return models.stream()
                .map(MovieRecommendationRestMapper::toDto)
                .toList();
    }
}
