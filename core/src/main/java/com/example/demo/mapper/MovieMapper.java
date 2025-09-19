package com.example.demo.mapper;

import com.example.demo.deepseek.DeepSeekMovieDto;
import com.example.demo.service.dto.MovieRecommendationDto;

import java.util.List;

public class MovieMapper {
    public static MovieRecommendationDto toDomain(DeepSeekMovieDto info) {
        return new MovieRecommendationDto(
                info.getTitle(),
                info.getYear(),
                info.getDirector(),
                info.getImdbRating(),
                info.getKinopoiskRating(),
                info.getDescription()
        );
    }
    public static List<MovieRecommendationDto> toDomainList(List<DeepSeekMovieDto> infos) {
        return infos.stream()
                .map(MovieMapper::toDomain)
                .toList();
    }
}
