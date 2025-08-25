package com.example.demo.service;

import com.example.demo.controller.dto.response.MovieRecommendationsResponse;
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
    public MovieRecommendationsResponse getMovieRecommendations(String genre, int count) {
        List<MovieRecommendationModel> movies = client.getRecommendations(PromptTemplatesUtil.generateGenrePrompt(genre, count), count);
        return MovieRecommendationsResponse.fromDomainList(movies);
    }

    @Override
    public MovieRecommendationsResponse getMovieRecommendationsOnEmotion(String emotion, int count) {
        List<MovieRecommendationModel> movies = client.getRecommendations(PromptTemplatesUtil.generateEmotionPrompt(emotion, count), count);
        return MovieRecommendationsResponse.fromDomainList(movies);
    }
}
