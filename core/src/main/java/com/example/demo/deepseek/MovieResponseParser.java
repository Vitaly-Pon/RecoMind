package com.example.demo.deepseek;

import com.example.demo.controller.dto.response.MovieRecommendationsResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class MovieResponseParser {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public List<MovieInfo> parse(String content) {
        // Убираем ```json``` и ``` блоки
        String cleaned = content
                .replaceAll("(?s)```json\\s*", "")
                .replaceAll("(?s)```", "")
                .trim();

        // Обертка для JSON с ключом "movies"
        MoviesWrapper wrapper = objectMapper.readValue(cleaned, MoviesWrapper.class);
        return wrapper.getMovies();
    }
    // Вложенный класс для обертки JSON
    private static class MoviesWrapper {
        @JsonProperty("movies")
        private List<MovieInfo> movies;

        public List<MovieInfo> getMovies() {
            return movies;
        }
    }
}