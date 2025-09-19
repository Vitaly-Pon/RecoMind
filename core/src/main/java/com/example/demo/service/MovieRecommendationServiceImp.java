package com.example.demo.service;

import com.example.demo.service.dto.MovieRecommendationDto;
import com.example.demo.deepseek.prompt.PromptTemplatesUtil;
import com.example.demo.deepseek.DeepSeekApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieRecommendationServiceImp implements MovieRecommendationService {

    private final DeepSeekApiClient client;

    @Override
    public List<MovieRecommendationDto> getMovieRecommendations(String genre, int count) {
        return client.getRecommendations(
                PromptTemplatesUtil.generateGenrePrompt(genre, count, List.of()),
                count
        );
    }

    @Override
    public List<MovieRecommendationDto> getMovieRecommendationsOnEmotion(String emotion, int count) {
        return client.getRecommendations(
                PromptTemplatesUtil.generateEmotionPrompt(emotion, count, List.of()),
                count
        );
    }

    @Override
    public List<MovieRecommendationDto> getMovieRecommendationsWithExclusions(String genre, int count, List<String> excluded) {
        return client.getRecommendations(
                PromptTemplatesUtil.generateGenrePrompt(genre, count, excluded),
                count
        );
    }

    @Override
    public List<MovieRecommendationDto> getMovieRecommendationsOnEmotionWithExclusions(String emotion, int count, List<String> excluded) {
        return client.getRecommendations(
                PromptTemplatesUtil.generateEmotionPrompt(emotion, count, excluded),
                count
        );
    }
}