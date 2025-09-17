package com.example.demo.mapper;

import com.example.demo.controller.response.UserMovieRatingDto;
import com.example.demo.repository.entity.MovieRating;

import java.util.List;

public class UserMovieRatingRestMapper {
    public static UserMovieRatingDto toDto(MovieRating entity) {
        return UserMovieRatingDto.builder()
                .id(entity.getId())
                .movieTitle(entity.getMovieTitle())
                .director(entity.getDirector())
                .year(entity.getMovieYear())
                .genre(entity.getGenre())
                .emotion(entity.getEmotion())
                .rating(entity.getRating())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static List<UserMovieRatingDto> toDtoList(List<MovieRating> entities) {
        return entities.stream()
                .map(UserMovieRatingRestMapper::toDto)
                .toList();
    }
}
