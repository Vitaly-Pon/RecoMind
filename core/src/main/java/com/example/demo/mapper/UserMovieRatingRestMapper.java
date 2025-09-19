package com.example.demo.mapper;

import com.example.demo.controller.response.UserMovieRatingDto;
import com.example.demo.repository.entity.Movie;
import com.example.demo.repository.entity.MovieRating;

import java.util.List;
public class UserMovieRatingRestMapper {

    // Преобразование одной сущности MovieRating в DTO
    public static UserMovieRatingDto toDto(MovieRating entity) {
        Movie movie = entity.getMovie(); // берём объект Movie
        return UserMovieRatingDto.builder()
                .id(entity.getId())
                .movieTitle(movie != null ? movie.getMovieTitle() : null)
                .director(movie != null ? movie.getDirector() : null)
                .year(movie != null ? movie.getMovieYear() : null)
                .genre(movie != null ? movie.getGenre() : null)
                .emotion(movie != null ? movie.getEmotion() : null)
                .rating(entity.getRating())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    // Преобразование списка MovieRating в список DTO
    public static List<UserMovieRatingDto> toDtoList(List<MovieRating> entities) {
        return entities.stream()
                .map(UserMovieRatingRestMapper::toDto)
                .toList();
    }
}
