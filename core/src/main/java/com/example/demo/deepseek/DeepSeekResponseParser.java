package com.example.demo.deepseek;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DeepSeekResponseParser {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public List<DeepSeekMovieDto> parse(String content) {
        // Убираем ```json``` и ``` блоки
        String cleaned = content
                .replaceAll("(?s)```json\\s*", "")
                .replaceAll("(?s)```", "")
                .trim();
        // Обертка для JSON с ключом "movies"
        DeepSeekMoviesResult wrapper = objectMapper.readValue(cleaned, DeepSeekMoviesResult.class);
        return wrapper.getMovies();
    }
}