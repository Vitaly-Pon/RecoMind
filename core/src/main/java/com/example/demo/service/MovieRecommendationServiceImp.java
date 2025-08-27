package com.example.demo.service;

import com.example.demo.controller.dto.response.MovieRecommendationResponse;
import com.example.demo.entity.MovieRecommendationModel;
import com.example.demo.util.PromptTemplatesUtil;
import com.example.demo.deepseek.DeepSeekApiClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieRecommendationServiceImp implements MovieRecommendationService {

    private final DeepSeekApiClient client;

    @Override
    public List<MovieRecommendationModel> getMovieRecommendations(String genre, int count) {
        return client.getRecommendations(
                PromptTemplatesUtil.generateGenrePrompt(genre, count),
                count
        );
    }

    @Override
    public List<MovieRecommendationModel> getMovieRecommendationsOnEmotion(String emotion, int count) {
        return client.getRecommendations(
                PromptTemplatesUtil.generateEmotionPrompt(emotion, count),
                count
        );
    }
}
