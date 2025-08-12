package com.example.demo.service;

import com.example.demo.client.dto.DeepSeekChatResponse;
import com.example.demo.util.PromptTemplates;
import com.example.demo.client.DeepSeekApiClient;
import com.example.demo.controller.dto.request.ChatMessageRequest;
import com.example.demo.client.dto.DeepSeekChatRequest;
import com.example.demo.controller.dto.response.MovieRecommendationsResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MovieRecommendationService {
    private final DeepSeekApiClient apiClient;

    public MovieRecommendationService(DeepSeekApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public MovieRecommendationsResponse getMovieRecommendations(String genre, int count) {
        String prompt = PromptTemplates.movieRecommendationPrompt(genre, count);

        DeepSeekChatRequest request = new DeepSeekChatRequest();
        request.setMessages(List.of(new ChatMessageRequest("user", prompt)));
        request.setMax_tokens(count * 100);

        DeepSeekChatResponse apiResponse = apiClient.getRecommendations(request);
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
        request.setMax_tokens(count * 100);

        DeepSeekChatResponse apiResponse = apiClient.getRecommendations(request);
        String content = apiResponse.getChoices().get(0).getMessage().getContent();

        List<String> recommendations = List.of(content.split("\\n\\n"));

        MovieRecommendationsResponse response = new MovieRecommendationsResponse();
        response.setRecommendations(recommendations);
        return response;
    }
}