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
    public MovieRecommendationsResponse getMovieRecommendations(String input, int count) {
        String prompt = PromptTemplates.generateGenrePrompt(input, count);
        return fetchRecommendations(prompt, count);
    }

    @Override
    public MovieRecommendationsResponse getMovieRecommendationsOnEmotion(String emotion, int count){
        String prompt = PromptTemplates.generateEmotionPrompt(emotion, count);
        return fetchRecommendations(prompt, count);
    }

    private MovieRecommendationsResponse fetchRecommendations(String prompt, int count){
        DeepSeekChatRequest request = new DeepSeekChatRequest();
        request.setMessages(List.of(new ChatMessageRequest("user", prompt)));
        request.setMaxTokens(count * 100);

        DeepSeekChatResponse apiResponse = deepSeekClient.getRecommendations(request);
        String content = apiResponse.getChoices().get(0).getMessage().getContent();

        List<String> recommendations = List.of(content.split("\\n{1,2}")).stream()
                .map(s -> s.replaceAll("\\*\\*", ""))  // убираем Markdown-звёздочки **
                .map(s -> s.replaceAll("\"", "")) // убирает кавычки
                .map(String::trim)                     // убираем лишние пробелы в начале и конце
                .filter(s -> !s.isEmpty())             // убираем пустые строки
                .toList();

        MovieRecommendationsResponse response = new MovieRecommendationsResponse();
        response.setRecommendations(recommendations);
        return response;
    }
}