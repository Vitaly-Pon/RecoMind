package com.example.demo;

import com.example.demo.controller.dto.response.ChatMessageResponse;
import com.example.demo.deepseek.DeepSeekApiClient;
import com.example.demo.deepseek.MovieInfo;
import com.example.demo.deepseek.MovieResponseParser;
import com.example.demo.deepseek.dto.DeepSeekChatResponse;
import com.example.demo.deepseek.dto.RecommendationChoice;
import com.example.demo.entity.MovieRecommendationModel;
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

    @MockitoBean
    private RestTemplate restTemplate;

    @MockitoBean
    private MovieResponseParser parser;

    @Autowired
    private DeepSeekApiClient apiClient;

    @Test
    void shouldReturnParsedMoviesWhenApiReturnsValidResponse() {
        // Мок ответа от RestTemplate
        DeepSeekChatResponse chatResponse = new DeepSeekChatResponse();
        RecommendationChoice choice = new RecommendationChoice();
        ChatMessageResponse message = new ChatMessageResponse();
        message.setContent("Recommended Content");
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

        // Мок парсера — возвращаем список MovieInfo
        List<MovieInfo> mockParsedList = List.of(
                new MovieInfo("Фильм 1", 2020, "Режиссер 1", 7.5, 8.5, "Описание 1"),
                new MovieInfo("Фильм 2", 2021, "Режиссер 2", 6.0, 7.0, "Описание 2")
        );
        when(parser.parse("Recommended Content")).thenReturn(mockParsedList);

        // Вызываем метод клиента
        List<MovieRecommendationModel> result = apiClient.getRecommendations("Ужасы", 3);

        assertNotNull(result);
        assert(result.size() == 2);
        assert(result.get(0).getTitle().equals("Фильм 1"));
        assert(result.get(1).getTitle().equals("Фильм 2"));

        verify(parser, times(1)).parse("Recommended Content");
    }
}