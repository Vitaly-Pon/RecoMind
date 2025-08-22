package com.example.demo.deepseek;

import com.example.demo.controller.dto.response.MovieRecommendationsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MovieResponseParser {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public MovieRecommendationsResponse parse(String content) {
        String cleaned = content
                .replaceAll("(?s)```json\\s*", "")
                .replaceAll("(?s)```", "")
                .trim();

        return objectMapper.readValue(cleaned, MovieRecommendationsResponse.class);
    }
}