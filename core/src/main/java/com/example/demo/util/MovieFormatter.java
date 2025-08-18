package com.example.demo.util;

import com.example.demo.deepseek.MovieInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class MovieFormatter {

    public List<String> formatMovies(List<MovieInfo> movies) {
        return IntStream.range(0, movies.size())
                .mapToObj(i -> (i + 1) + ". " + formatMovieInfo(movies.get(i)))
                .toList();
    }

    public String formatMovieInfo(MovieInfo movie) {
        return String.format("%s (%s, %s) (IMDB: %s, КП: %s) - %s",
                movie.getTitle() != null ? movie.getTitle() : "Без названия",
                movie.getYear() != null ? movie.getYear() : "Год неизвестен",
                movie.getDirector() != null ? movie.getDirector() : "Неизвестный режиссер",
                formatRating(movie.getImdbRating()),
                formatRating(movie.getKinopoiskRating()),
                movie.getDescription() != null ? movie.getDescription() : "");
    }

    private String formatRating(Double rating) {
        return rating != null ? String.format("%.2f", rating) : "N/A";
    }
}

