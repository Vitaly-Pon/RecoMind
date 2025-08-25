package com.example.demo.mapper;

import com.example.demo.deepseek.MovieInfo;
import com.example.demo.entity.MovieRecommendationModel;

import java.util.List;

public class MovieMapper {
    public static MovieRecommendationModel toDomain(MovieInfo info) {
        return new MovieRecommendationModel(
                info.getTitle(),
                info.getYear(),
                info.getDirector(),
                info.getImdbRating(),
                info.getKinopoiskRating(),
                info.getDescription()
        );
    }
    public static List<MovieRecommendationModel> toDomainList(List<MovieInfo> infos) {
        return infos.stream()
                .map(MovieMapper::toDomain)
                .toList();
    }
}
