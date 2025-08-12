package com.example.demo.service;

import com.example.demo.deepseek.dto.DeepSeekChatResponse;
import com.example.demo.util.PromptTemplates;
import com.example.demo.deepseek.DeepSeekApiClient;
import com.example.demo.controller.dto.request.ChatMessageRequest;
import com.example.demo.deepseek.dto.DeepSeekChatRequest;
import com.example.demo.controller.dto.response.MovieRecommendationsResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MovieRecommendationService {
    private final DeepSeekApiClient deepSeekClient;

    public MovieRecommendationService(DeepSeekApiClient deepSeekClient) {
        this.deepSeekClient = deepSeekClient;
    }

    public MovieRecommendationsResponse getMovieRecommendations(String genre, int count) {
        String prompt = PromptTemplates.movieRecommendationPrompt(genre, count);

        DeepSeekChatRequest request = new DeepSeekChatRequest();
        request.setMessages(List.of(new ChatMessageRequest("user", prompt)));
        request.setMaxTokens(count * 100);

        DeepSeekChatResponse apiResponse = deepSeekClient.getRecommendations(request);
        String content = apiResponse.getChoices().get(0).getMessage().getContent();

        List<String> recommendations = List.of(content.split("\\n\\n"));

        MovieRecommendationsResponse response = new MovieRecommendationsResponse();
        response.setRecommendations(recommendations);
        return response;
    }

    public MovieRecommendationsResponse getMovieRecommendationsOnEmotion(String emotion, int count) {
        String prompt = PromptTemplates.movieRecommendationOnEmotionPrompt(emotion, count);

        DeepSeekChatRequest request = new DeepSeekChatRequest();
        request.setMessages(List.of(new ChatMessageRequest("user", prompt)));
        request.setMaxTokens(count * 100);

        DeepSeekChatResponse apiResponse = deepSeekClient.getRecommendations(request);
        String content = apiResponse.getChoices().get(0).getMessage().getContent();

        List<String> recommendations = List.of(content.split("\\n\\n"));

        MovieRecommendationsResponse response = new MovieRecommendationsResponse();
        response.setRecommendations(recommendations);
        return response;
    }
}