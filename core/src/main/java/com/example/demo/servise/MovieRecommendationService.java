package com.example.demo.servise;

import com.example.demo.util.PromptTemplates;
import com.example.demo.client.DeepSeekApiClient;
import com.example.demo.controller.dto.request.ChatMessageRequest;
import com.example.demo.client.dto.DeepSeekChatRequest;
import com.example.demo.controller.dto.response.MovieRecommendationsResponse;
import org.springframework.stereotype.Service;

import java.util.List;
// РЕСПОнс реквест в контроллере, дто в сервисе, сущность непосредственно в репозитории
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

        var apiResponse = apiClient.getRecommendations(request);

        var response = new MovieRecommendationsResponse();
        response.setContent(apiResponse.getChoices().get(0).getMessage().getContent());
        return response;
    }
}