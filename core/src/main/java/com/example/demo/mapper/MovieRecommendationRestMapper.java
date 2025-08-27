package com.example.demo.mapper;

import com.example.demo.service.dto.MovieRecommendationDto;

import java.util.List;

public class MovieRecommendationRestMapper {
    public static com.example.demo.controller.response.MovieRecommendationDto toDto(MovieRecommendationDto model) {
        return com.example.demo.controller.response.MovieRecommendationDto.builder()
                .title(model.getTitle())
                .year(model.getYear())
                .director(model.getDirector())
                .imdbRating(model.getImdbRating())
                .kinopoiskRating(model.getKinopoiskRating())
                .description(model.getDescription())
                .build();
    }

    public static List<com.example.demo.controller.response.MovieRecommendationDto> toDtoList(List<MovieRecommendationDto> models) {
        return models.stream()
                .map(MovieRecommendationRestMapper::toDto)
                .toList();
    }
}
