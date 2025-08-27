package com.example.demo.service;

import com.example.demo.service.dto.MovieRecommendationDto;
import com.example.demo.deepseek.prompt.PromptTemplatesUtil;
import com.example.demo.deepseek.DeepSeekApiClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieRecommendationServiceImp implements MovieRecommendationService {

    private final DeepSeekApiClient client;

    @Override
    public List<MovieRecommendationDto> getMovieRecommendations(String genre, int count) {
        return client.getRecommendations(
                PromptTemplatesUtil.generateGenrePrompt(genre, count),
                count
        );
    }

    @Override
    public List<MovieRecommendationDto> getMovieRecommendationsOnEmotion(String emotion, int count) {
        return client.getRecommendations(
                PromptTemplatesUtil.generateEmotionPrompt(emotion, count),
                count
        );
    }
}
