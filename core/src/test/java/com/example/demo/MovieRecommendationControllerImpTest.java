package com.example.demo;

import com.example.demo.controller.dto.request.MovieRecommendationRequest;
import com.example.demo.controller.dto.response.MovieRecommendationsResponse;
import com.example.demo.deepseek.MovieInfo;
import com.example.demo.exception.DeepSeekApiException;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
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
    void shouldFailValidationIfGenreIsEmpty() throws Exception {
        MovieRecommendationRequest request = new MovieRecommendationRequest();
        request.setGenre("");
        request.setCount(0);

        mockMvc.perform(post("/movies/genre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("Bad Request")))
                .andExpect(jsonPath("$.message", is("Проверка не пройдена")))
                .andExpect(jsonPath("$.path", is("/movies/genre")))
                .andExpect(jsonPath("$.details.genre", is("Жанр не должен быть пустым")))
                .andExpect(jsonPath("$.details.count", is("Минимум один фильм")));
    }

    @Test
    void shouldReturnRecommendationsWhenValidRequest() throws Exception {
        MovieRecommendationsResponse movieRecommendationsResponse = new MovieRecommendationsResponse(List.of(
                new MovieInfo("Фильм 1", 2020, "Режиссер 1", 7.5, 8.5, "Описание 1"),
                new MovieInfo("Фильм 2", 2021, "Режиссер 2", 6.0, 7.0, "Описание 2"),
                new MovieInfo("Фильм 3", 2022, "Режиссер 3", 8.0, 9.5, "Описание 3")));

        when(service.getMovieRecommendations("Комедия", 3)).thenReturn(movieRecommendationsResponse);

       MovieRecommendationRequest request = new MovieRecommendationRequest();
       request.setGenre("Комедия");
       request.setCount(3);

        mockMvc.perform(
                post("/movies/genre")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(jsonPath("$.movies", hasSize(3)))
                .andExpect(jsonPath("$.movies[0].title", is("Фильм 1")))
                .andExpect(jsonPath("$.movies[0].year", is(2020)))
                .andExpect(jsonPath("$.movies[0].director", is("Режиссер 1")))
                .andExpect(jsonPath("$.movies[1].title", is("Фильм 2")))
                .andExpect(jsonPath("$.movies[2].title", is("Фильм 3")));

        verify(deepSeekApiClient, times(1)).getRecommendations(any(), anyInt());
    }
    @Test
    void shouldFailValidationIfCountToo() throws Exception{
        MovieRecommendationRequest request = new MovieRecommendationRequest();
        request.setGenre("Ужасы");
        request.setCount(99);

        mockMvc.perform(post("/movies/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("Bad Request")))
                .andExpect(jsonPath("$.message", is("Проверка не пройдена")))
                .andExpect(jsonPath("$.details.count", is("Максимум десять фильмов")));

    }
    @Test
    void shouldReturnDefaultValueWhenThereIsNoCountField() throws Exception {
        MovieRecommendationsResponse movieRecommendationsResponse = new MovieRecommendationsResponse(List.of(
                new MovieInfo("Фильм 1", 2020, "Режиссер 1", 7.5, 8.5, "Описание 1"),
                new MovieInfo("Фильм 2", 2021, "Режиссер 2", 6.0, 7.0, "Описание 2"),
                new MovieInfo("Фильм 3", 2022, "Режиссер 3", 8.0, 9.5, "Описание 3"),
                new MovieInfo("Фильм 4", 2023, "Режиссер 4", 6.0, 7.0, "Описание 4"),
                new MovieInfo("Фильм 5", 2024, "Режиссер 5", 8.0, 9.5, "Описание 5")));

        when(service.getMovieRecommendations("Ужасы", 5)).thenReturn(movieRecommendationsResponse);

        MovieRecommendationRequest request = new MovieRecommendationRequest();
        request.setGenre("Ужасы");

        mockMvc.perform(
                        post("/movies/genre")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(jsonPath("$.movies", hasSize(5)))
                .andExpect(jsonPath("$.movies[0].title", is("Фильм 1")))
                .andExpect(jsonPath("$.movies[1].title", is("Фильм 2")))
                .andExpect(jsonPath("$.movies[2].title", is("Фильм 3")))
                .andExpect(jsonPath("$.movies[3].title", is("Фильм 4")))
                .andExpect(jsonPath("$.movies[4].title", is("Фильм 5")));

        verify(deepSeekApiClient, times(1)).getRecommendations(any(), anyInt());
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
}
