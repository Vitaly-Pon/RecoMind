package com.example.demo.service;

import com.example.demo.util.PromptTemplatesUtil;
import com.example.demo.deepseek.DeepSeekApiClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovieRecommendationServiceImp implements MovieRecommendationService {

    private final DeepSeekApiClient client;

    @Override
    public Object getMovieRecommendations(String genre, int count) {
        return client.getRecommendations(PromptTemplatesUtil.generateGenrePrompt(genre, count), count);
    }

    @Override
    public Object getMovieRecommendationsOnEmotion(String emotion, int count) {
        return client.getRecommendations(PromptTemplatesUtil.generateEmotionPrompt(emotion, count), count);
    }
}
