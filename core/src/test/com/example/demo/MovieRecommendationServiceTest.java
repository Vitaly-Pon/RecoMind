package com.example.demo;

import com.example.demo.client.DeepSeekApiClient;
import com.example.demo.client.dto.DeepSeekChatRequest;
import com.example.demo.client.dto.DeepSeekChatResponse;
import com.example.demo.client.dto.RecommendationChoice;
import com.example.demo.controller.dto.response.ChatMessageResponse;
import com.example.demo.controller.dto.response.MovieRecommendationsResponse;
import com.example.demo.service.MovieRecommendationService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.InjectMocks;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
public class MovieRecommendationServiceTest {

    @Mock
    private DeepSeekApiClient apiClient;

    @InjectMocks
    private MovieRecommendationService service;

    @Test
    void shouldReturnRecommendation(){

        var mockResponse = new DeepSeekChatResponse();

        ChatMessageResponse message = new ChatMessageResponse();
        message.setContent("\"Фильм 1\\n\\nФильм 2\"");

        RecommendationChoice choice = new RecommendationChoice();
        choice.setMessage(message);

        mockResponse.setChoices(List.of(choice));

        when(apiClient.getRecommendations(any())).thenReturn(mockResponse);

        MovieRecommendationsResponse responseResult = service.getMovieRecommendations("Комедия",2);

        assertThat(responseResult.getRecommendations()).containsExactly("Фильм 1", "Фильм 2");

        verify(apiClient, times(1)).getRecommendations(any());
    }
}
