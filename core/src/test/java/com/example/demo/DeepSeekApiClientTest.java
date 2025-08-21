package com.example.demo;

import com.example.demo.config.DeepSeekConfig;
import com.example.demo.controller.dto.response.ChatMessageResponse;
import com.example.demo.controller.dto.response.MovieRecommendationsResponse;
import com.example.demo.deepseek.DeepSeekApiClient;
import com.example.demo.deepseek.MovieResponseParser;
import com.example.demo.deepseek.dto.DeepSeekChatResponse;
import com.example.demo.deepseek.dto.RecommendationChoice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DeepSeekApiClientTest {

    @Autowired
    private DeepSeekConfig config;

    @MockitoBean
    private RestTemplate restTemplate;

    @MockitoBean
    private MovieResponseParser parser;

    @Autowired
    private DeepSeekApiClient apiClient;

    @Test
    void shouldReturnParsedMoviesWhenApiReturnsValidResponse() {
        // Создаём мок ответа от RestTemplate
        DeepSeekChatResponse chatResponse = new DeepSeekChatResponse();
        RecommendationChoice choice = new RecommendationChoice();
        ChatMessageResponse message = new ChatMessageResponse();

        message.setContent("Recommended Content");  // <-- используем сеттер
        choice.setMessage(message);
        chatResponse.setChoices(List.of(choice));

        ResponseEntity<DeepSeekChatResponse> responseEntity =
                new ResponseEntity<>(chatResponse, HttpStatus.OK);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(DeepSeekChatResponse.class)
        )).thenReturn(responseEntity);

        // Мок парсера
        MovieRecommendationsResponse mockParsed = new MovieRecommendationsResponse(List.of());
        when(parser.parse("Recommended Content")).thenReturn(mockParsed);

        // Вызов метода
        MovieRecommendationsResponse result = apiClient.getRecommendations("Ужасы", 3);

        assertNotNull(result);
        verify(parser, times(1)).parse("Recommended Content");
    }
}