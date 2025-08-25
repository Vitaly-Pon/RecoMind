package com.example.demo.mapper;

import com.example.demo.deepseek.DeepSeekMovieDto;
import com.example.demo.entity.MovieRecommendationModel;

import java.util.List;

public class MovieMapper {
    public static MovieRecommendationModel toDomain(DeepSeekMovieDto info) {
        return new MovieRecommendationModel(
                info.getTitle(),
                info.getYear(),
                info.getDirector(),
                info.getImdbRating(),
                info.getKinopoiskRating(),
                info.getDescription()
        );
    }
    public static List<MovieRecommendationModel> toDomainList(List<DeepSeekMovieDto> infos) {
        return infos.stream()
                .map(MovieMapper::toDomain)
                .toList();
    }
}
