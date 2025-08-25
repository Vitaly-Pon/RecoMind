package com.example.demo;

import com.example.demo.controller.dto.request.MovieRecByEmotionRequest;
import com.example.demo.controller.dto.request.MovieRecommendationRequest;
import com.example.demo.deepseek.MovieInfo;;
import com.example.demo.exception.DeepSeekApiException;
import com.example.demo.mapper.MovieMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MovieRecommendationModelControllerImpTest extends AbstractControllerTest {

    @Test
    void shouldFailValidationIfGenreIsEmpty() throws Exception {
        MovieRecommendationRequest request = new MovieRecommendationRequest();
        request.setGenre("");
        request.setCount(0);

        mockMvc.perform(post("/movies/genre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.genre", is("Жанр не должен быть пустым")))
                .andExpect(jsonPath("$.details.count", is("Минимум один фильм")));
    }

    @Test
    void shouldReturnRecommendationsWhenDeepSeekReturnsData() throws Exception {
        // Мокаем DeepSeekApiClient
        List<MovieInfo> mockMovies = List.of(
                new MovieInfo("Фильм 1", 2020, "Режиссер 1", 7.5, 8.5, "Описание 1"),
                new MovieInfo("Фильм 2", 2021, "Режиссер 2", 6.0, 7.0, "Описание 2"),
                new MovieInfo("Фильм 3", 2022, "Режиссер 3", 8.0, 9.5, "Описание 3")
        );
        when(deepSeekApiClient.getRecommendations(anyString(), anyInt()))
                .thenReturn(mockMovies.stream().map(MovieMapper::toDomain).toList());

        MovieRecommendationRequest request = new MovieRecommendationRequest();
        request.setGenre("Комедия");
        request.setCount(3);

        mockMvc.perform(post("/movies/genre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movies", hasSize(3)))
                .andExpect(jsonPath("$.movies[0].title", is("Фильм 1")))
                .andExpect(jsonPath("$.movies[2].title", is("Фильм 3")));

        verify(deepSeekApiClient, times(1)).getRecommendations(anyString(), anyInt());
    }

    @Test
    void shouldReturnDefaultCountWhenMissing() throws Exception {
        List<MovieInfo> mockMovies = List.of(
                new MovieInfo("Фильм 1", 2020, "Режиссер 1", 7.5, 8.5, "Описание 1"),
                new MovieInfo("Фильм 2", 2021, "Режиссер 2", 6.0, 7.0, "Описание 2"),
                new MovieInfo("Фильм 3", 2022, "Режиссер 3", 8.0, 9.5, "Описание 3"),
                new MovieInfo("Фильм 4", 2019, "Режиссер 4", 7.0, 8.0, "Описание 4"),
                new MovieInfo("Фильм 5", 2021, "Режиссер 5", 6.5, 7.5, "Описание 5")
        );
        when(deepSeekApiClient.getRecommendations(anyString(), anyInt()))
                .thenReturn(mockMovies.stream().map(MovieMapper::toDomain).toList());

        MovieRecommendationRequest request = new MovieRecommendationRequest();
        request.setGenre("Ужасы");

        mockMvc.perform(post("/movies/genre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movies", hasSize(5)))
                .andExpect(jsonPath("$.movies[0].title", is("Фильм 1")))
                .andExpect(jsonPath("$.movies[4].title", is("Фильм 5")));

        verify(deepSeekApiClient, times(1)).getRecommendations(anyString(), anyInt());
    }

    @Test
    void shouldReturnServiceUnavailableWhenDeepSeekFails() throws Exception {
        when(deepSeekApiClient.getRecommendations(anyString(), anyInt()))
                .thenThrow(new DeepSeekApiException("DeepSeek API недоступен"));

        MovieRecommendationRequest request = new MovieRecommendationRequest();
        request.setGenre("Ужасы");
        request.setCount(3);

        mockMvc.perform(post("/movies/genre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.status").value(503))
                .andExpect(jsonPath("$.error").value("Service Unavailable"))
                .andExpect(jsonPath("$.message").value("DeepSeek API недоступен"))
                .andExpect(jsonPath("$.path").value("/movies/genre"))
                .andExpect(jsonPath("$.details").doesNotExist());

        verify(deepSeekApiClient, times(1)).getRecommendations(anyString(), anyInt());
    }

    @Test
    void shouldFailValidationIfCountMoreThanTen() throws Exception {
        MovieRecommendationRequest request = new MovieRecommendationRequest();
        request.setGenre("Ужасы");
        request.setCount(99);

        mockMvc.perform(post("/movies/genre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.count", is("Максимум десять фильмов")));
    }
    // Тесты рекомендаций по ЭМОЦИИ
    @Test
    void shouldFailValidationIfEmotionIsEmpty() throws Exception {
        MovieRecByEmotionRequest request = new MovieRecByEmotionRequest();
        request.setEmotion("");
        request.setCount(0);

        mockMvc.perform(post("/movies/emotion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.emotion", is("Поле не должно быть пустым")))
                .andExpect(jsonPath("$.details.count", is("Минимум один фильм")));
    }
    @Test
    void shouldReturnRecommendationsForEmotion() throws Exception {
        List<MovieInfo> mockMovies = List.of(
                new MovieInfo("Фильм 1", 2020, "Режиссер 1", 7.5, 8.5, "Описание 1"),
                new MovieInfo("Фильм 2", 2021, "Режиссер 2", 6.0, 7.0, "Описание 2"),
                new MovieInfo("Фильм 3", 2022, "Режиссер 3", 8.0, 9.5, "Описание 3")
        );
        when(deepSeekApiClient.getRecommendations(anyString(), anyInt()))
                .thenReturn(mockMovies.stream().map(MovieMapper::toDomain).toList());

        MovieRecByEmotionRequest request = new MovieRecByEmotionRequest();
        request.setEmotion("Веселое");
        request.setCount(3);

        mockMvc.perform(post("/movies/emotion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movies", hasSize(3)))
                .andExpect(jsonPath("$.movies[0].title", is("Фильм 1")))
                .andExpect(jsonPath("$.movies[2].title", is("Фильм 3")));

        verify(deepSeekApiClient, times(1)).getRecommendations(anyString(), anyInt());
    }
    @Test
    void shouldReturnDefaultCountForEmotionWhenMissing() throws Exception {
        List<MovieInfo> mockMovies = List.of(
                new MovieInfo("Фильм 1", 2020, "Режиссер 1", 7.5, 8.5, "Описание 1"),
                new MovieInfo("Фильм 2", 2021, "Режиссер 2", 6.0, 7.0, "Описание 2"),
                new MovieInfo("Фильм 3", 2022, "Режиссер 3", 8.0, 9.5, "Описание 3"),
                new MovieInfo("Фильм 4", 2019, "Режиссер 4", 7.0, 8.0, "Описание 4"),
                new MovieInfo("Фильм 5", 2021, "Режиссер 5", 6.5, 7.5, "Описание 5")
        );
        when(deepSeekApiClient.getRecommendations(anyString(), anyInt()))
                .thenReturn(mockMovies.stream().map(MovieMapper::toDomain).toList());

        MovieRecByEmotionRequest request = new MovieRecByEmotionRequest();
        request.setEmotion("Грустное");

        mockMvc.perform(post("/movies/emotion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movies", hasSize(5)))
                .andExpect(jsonPath("$.movies[0].title", is("Фильм 1")))
                .andExpect(jsonPath("$.movies[4].title", is("Фильм 5")));

        verify(deepSeekApiClient, times(1)).getRecommendations(anyString(), anyInt());
    }

    @Test
    void shouldReturnServiceUnavailableWhenDeepSeekFailsForEmotion() throws Exception {
        when(deepSeekApiClient.getRecommendations(anyString(), anyInt()))
                .thenThrow(new DeepSeekApiException("DeepSeek API недоступен"));

        MovieRecByEmotionRequest request = new MovieRecByEmotionRequest();
        request.setEmotion("Грустное");
        request.setCount(3);

        mockMvc.perform(post("/movies/emotion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.status").value(503))
                .andExpect(jsonPath("$.error").value("Service Unavailable"))
                .andExpect(jsonPath("$.message").value("DeepSeek API недоступен"))
                .andExpect(jsonPath("$.path").value("/movies/emotion"))
                .andExpect(jsonPath("$.details").doesNotExist());

        verify(deepSeekApiClient, times(1)).getRecommendations(anyString(), anyInt());
    }

    @Test
    void shouldFailValidationIfCountMoreThanTenForEmotion() throws Exception {
        MovieRecByEmotionRequest request = new MovieRecByEmotionRequest();
        request.setEmotion("Грустное");
        request.setCount(99);

        mockMvc.perform(post("/movies/emotion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.count", is("Максимум десять фильмов")));
    }
}
