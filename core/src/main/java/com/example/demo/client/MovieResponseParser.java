package com.example.demo.deepseek;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MovieResponseParser {
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public MovieApiResponse parse(String content) {
        // Убираем обертки ```json ... ```
        String cleaned = content
                .replaceAll("(?s)```json\\s*", "")
                .replaceAll("(?s)```", "")
                .trim();

        // Парсим очищенный JSON в объект
        return objectMapper.readValue(cleaned, MovieApiResponse.class);
    }

    public String formatMovieInfo(MovieInfo movie) {
        return String.format("%s (%d, %s) (IMDB: %s, КП: %s) - %s",
                movie.getTitle() != null ? movie.getTitle() : "Без названия",
                movie.getYear() != null ? movie.getYear() : 0,
                movie.getDirector() != null ? movie.getDirector() : "Неизвестный режиссер",
                formatRating(movie.getImdbRating()),
                formatRating(movie.getKinopoiskRating()),
                movie.getDescription() != null ? movie.getDescription() : "");
    }

    private String formatRating(Double rating) {
        return rating != null ? String.format("%.2f", rating) : "N/A";
    }
}