package com.example.demo.service;

import com.example.demo.deepseek.dto.DeepSeekChatResponse;
import com.example.demo.util.PromptTemplates;
import com.example.demo.deepseek.DeepSeekApiClient;
import com.example.demo.controller.dto.request.ChatMessageRequest;
import com.example.demo.deepseek.dto.DeepSeekChatRequest;
import com.example.demo.controller.dto.response.MovieRecommendationsResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class MovieRecommendationServiceImp implements MovieRecommendationService {
    private final DeepSeekApiClient deepSeekClient;

    @Override
    public MovieRecommendationsResponse getMovieRecommendations(String genre, int count) {
        String prompt = PromptTemplates.generateGenrePrompt(genre, count);
        return deepSeekClient.getRecommendations(prompt, count);
    }

    @Override
    public MovieRecommendationsResponse getMovieRecommendationsOnEmotion(String emotion, int count){
        String prompt = PromptTemplates.generateEmotionPrompt(emotion, count);
        return deepSeekClient.getRecommendations(prompt, count);
    }
}