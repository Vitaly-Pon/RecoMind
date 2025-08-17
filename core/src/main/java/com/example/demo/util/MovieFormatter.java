package com.example.demo.util;

import com.example.demo.deepseek.MovieInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class MovieFormatter {
    // Форматирует список фильмов с номерами
    public List<String> formatMovies(List<MovieInfo> movies) {
        return IntStream.range(0, movies.size())
                .mapToObj(i -> (i + 1) + ". " + formatMovieInfo(movies.get(i)))
                .toList();
    }

    // Форматирует один фильм в виде: Название (год, режиссер) (IMDB, КП) - описание
    public String formatMovieInfo(MovieInfo movie) {
        return String.format("%s (%s, %s) (IMDB: %s, КП: %s) - %s",
                movie.getTitle() != null ? movie.getTitle() : "Без названия",
                movie.getYear() != null ? movie.getYear() : "Год неизвестен",
                movie.getDirector() != null ? movie.getDirector() : "Неизвестный режиссер",
                formatRating(movie.getImdbRating()),
                formatRating(movie.getKinopoiskRating()),
                movie.getDescription() != null ? movie.getDescription() : "");
    }

    // Форматирует рейтинг с 2 знаками после запятой
    private String formatRating(Double rating) {
        return rating != null ? String.format("%.2f", rating) : "N/A";
    }
}
