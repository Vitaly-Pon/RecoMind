package com.example.demo;


import com.example.demo.controller.request.MovieRecommendationRequest;
import com.example.demo.deepseek.DeepSeekMovieDto;
import com.example.demo.mapper.MovieMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.hamcrest.Matchers.hasEntry;
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

public class MovieRecommendationControllerImpTest extends AbstractControllerTest {

    @Test
    void shouldFailValidationIfNeitherGenreNorEmotionProvided() throws Exception {
        MovieRecommendationRequest request = new MovieRecommendationRequest();
        request.setGenre(null);
        request.setEmotion(null);
        request.setCount(3); // валидный count

        mockMvc.perform(post("/movie/genre/recommendations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details", hasEntry("genreOrEmotionPresent", "Нужно указать хотя бы genre или emotion")));
    }

    @Test
    void shouldFailValidationIfCountLessThanMin() throws Exception {
        MovieRecommendationRequest request = new MovieRecommendationRequest();
        request.setGenre("Комедия");
        request.setCount(0); // меньше min

        mockMvc.perform(post("/movie/genre/recommendations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.count", is("Минимум один фильм")));
    }

    @Test
    void shouldFailValidationIfCountMoreThanMax() throws Exception {
        MovieRecommendationRequest request = new MovieRecommendationRequest();
        request.setGenre("Комедия");
        request.setCount(99); // больше max

        mockMvc.perform(post("/movie/genre/recommendations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.count", is("Максимум десять фильмов")));
    }

    @Test
    void shouldReturnRecommendationsForGenre() throws Exception {
        List<DeepSeekMovieDto> mockMovies = List.of(
                new DeepSeekMovieDto("Фильм 1", 2020, "Режиссер 1", 7.5, 8.5, "Описание 1"),
                new DeepSeekMovieDto("Фильм 2", 2021, "Режиссер 2", 6.0, 7.0, "Описание 2"),
                new DeepSeekMovieDto("Фильм 3", 2022, "Режиссер 3", 8.0, 9.5, "Описание 3")
        );
        when(deepSeekApiClient.getRecommendations(anyString(), anyInt()))
                .thenReturn(mockMovies.stream().map(MovieMapper::toDomain).toList());

        MovieRecommendationRequest request = new MovieRecommendationRequest();
        request.setGenre("Комедия");
        request.setCount(3);

        mockMvc.perform(post("/movie/genre/recommendations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movies", hasSize(3)))
                .andExpect(jsonPath("$.movies[0].title", is("Фильм 1")))
                .andExpect(jsonPath("$.movies[2].title", is("Фильм 3")));

        verify(deepSeekApiClient, times(1)).getRecommendations(anyString(), anyInt());
    }

    @Test
    void shouldReturnRecommendationsForEmotion() throws Exception {
        List<DeepSeekMovieDto> mockMovies = List.of(
                new DeepSeekMovieDto("Фильм 1", 2020, "Режиссер 1", 7.5, 8.5, "Описание 1"),
                new DeepSeekMovieDto("Фильм 2", 2021, "Режиссер 2", 6.0, 7.0, "Описание 2"),
                new DeepSeekMovieDto("Фильм 3", 2022, "Режиссер 3", 8.0, 9.5, "Описание 3")
        );
        when(deepSeekApiClient.getRecommendations(anyString(), anyInt()))
                .thenReturn(mockMovies.stream().map(MovieMapper::toDomain).toList());

        MovieRecommendationRequest request = new MovieRecommendationRequest();
        request.setEmotion("Веселое");
        request.setCount(3);

        mockMvc.perform(post("/movie/emotion/recommendations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movies", hasSize(3)))
                .andExpect(jsonPath("$.movies[0].title", is("Фильм 1")))
                .andExpect(jsonPath("$.movies[2].title", is("Фильм 3")));

        verify(deepSeekApiClient, times(1)).getRecommendations(anyString(), anyInt());
    }
}