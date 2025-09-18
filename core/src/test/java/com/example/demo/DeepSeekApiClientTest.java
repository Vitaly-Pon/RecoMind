package com.example.demo;

import com.example.demo.controller.response.ChatMessageResponse;
import com.example.demo.deepseek.DeepSeekApiClient;
import com.example.demo.deepseek.DeepSeekMovieDto;
import com.example.demo.deepseek.DeepSeekResponseParser;
import com.example.demo.deepseek.dto.DeepSeekChatResponse;
import com.example.demo.deepseek.dto.DeepSeekChoice;
import com.example.demo.service.dto.MovieRecommendationDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private DeepSeekResponseParser parser;

    @Autowired
    private DeepSeekApiClient apiClient;

    @Test
    void shouldReturnParsedMoviesWhenApiReturnsValidResponse() {
        //  Мокируем ответ RestTemplate
        DeepSeekChatResponse chatResponse = new DeepSeekChatResponse();
        DeepSeekChoice choice = new DeepSeekChoice();
        ChatMessageResponse message = new ChatMessageResponse();
        message.setContent("Recommended Content"); // <- именно это вернёт send()
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

        // Мокируем парсер на результат, который вернёт getRecommendations
        List<DeepSeekMovieDto> mockParsedList = List.of(
                new DeepSeekMovieDto("Фильм 1", 2020, "Режиссер 1", 7.5, 8.5, "Описание 1"),
                new DeepSeekMovieDto("Фильм 2", 2021, "Режиссер 2", 6.0, 7.0, "Описание 2")
        );
        when(parser.parse("Recommended Content")).thenReturn(mockParsedList);

        // Вызываем метод
        List<MovieRecommendationDto> result = apiClient.getRecommendations("Ужасы", 2);

        //  Проверяем результат
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Фильм 1", result.get(0).getTitle());
        assertEquals("Фильм 2", result.get(1).getTitle());

        verify(parser, times(1)).parse("Recommended Content");
    }
}