package com.example.demo.service;

import com.example.demo.util.MovieFormatter;
import com.example.demo.util.PromptTemplates;
import com.example.demo.deepseek.DeepSeekApiClient;
import com.example.demo.controller.dto.response.MovieRecommendationsResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovieRecommendationServiceImp implements MovieRecommendationService {

    private final DeepSeekApiClient client;
    private final MovieFormatter formatter;

    @Override
    public Object getMovieRecommendations(String genre, int count, String format) {
        var response = client.getRecommendations(PromptTemplates.generateGenrePrompt(genre, count), count);
        return formatResponse(response, format);
    }

    @Override
    public Object getMovieRecommendationsOnEmotion(String emotion, int count, String format) {
        var response = client.getRecommendations(PromptTemplates.generateEmotionPrompt(emotion, count), count);
        return formatResponse(response, format);
    }

    private Object formatResponse(MovieRecommendationsResponse response, String format) {
        if ("text".equalsIgnoreCase(format)) {
            return String.join("\n", formatter.formatMovies(response.getMovies()));
        }
        return response;
    }
}
