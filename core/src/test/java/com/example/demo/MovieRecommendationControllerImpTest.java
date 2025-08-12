package com.example.demo;

import com.example.demo.controller.dto.request.MovieRecommendationRequest;
import com.example.demo.controller.dto.response.ChatMessageResponse;
import com.example.demo.controller.dto.response.MovieRecommendationsResponse;
import com.example.demo.deepseek.DeepSeekApiClient;
import com.example.demo.deepseek.dto.DeepSeekChatResponse;
import com.example.demo.deepseek.dto.RecommendationChoice;
import com.example.demo.service.MovieRecommendationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieRecommendationControllerImpTest {
    @Autowired
    private MovieRecommendationService movieRecommendationService;
    @Autowired
    private TestRestTemplate restTemplate;

    @MockitoBean
    private DeepSeekApiClient deepSeekApiClient;

    @Test
    void shouldFailValidationIfGenreIsEmptyAndCountIsInvalid(){
        MovieRecommendationRequest movieRequest = new MovieRecommendationRequest();
        movieRequest.setGenre("");
        movieRequest.setCount(0);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MovieRecommendationRequest> entity = new HttpEntity<>(movieRequest, headers);

        ResponseEntity<String> response = restTemplate
                .postForEntity("/api/movies/recommendInGenre", entity, String.class);


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Жанр не должен быть пустым", "Минимум один фильм");
    }

    @Test
    void shouldReturnRecommendationsWhenValidRequest() {
        DeepSeekChatResponse mockResponse = new DeepSeekChatResponse();

        RecommendationChoice choice = new RecommendationChoice();
        ChatMessageResponse message = new ChatMessageResponse();
        message.setContent("Фильм 1\n\nФильм 2\n\nФильм 3");
        choice.setMessage(message);

        mockResponse.setChoices(List.of(choice));

        Mockito.when(deepSeekApiClient.getRecommendations(Mockito.any()))
                .thenReturn(mockResponse);

        MovieRecommendationRequest request = new MovieRecommendationRequest();
        request.setGenre("Комедия");
        request.setCount(3);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MovieRecommendationRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<MovieRecommendationsResponse> response = restTemplate
                .postForEntity("/api/movies/recommendInGenre", entity, MovieRecommendationsResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getRecommendations()).hasSize(3)
                .containsExactly("Фильм 1", "Фильм 2", "Фильм 3");
    }
}
