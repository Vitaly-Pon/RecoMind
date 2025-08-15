package com.example.demo;

import com.example.demo.controller.dto.request.MovieRecommendationRequest;
import com.example.demo.controller.dto.response.MovieRecommendationsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MovieRecommendationControllerImpTest extends AbstractControllerTest {

    @Test
    void shouldFailValidationIfGenreIsEmptyAndCountIsInvalid() throws Exception{
        MovieRecommendationRequest movieRequest = new MovieRecommendationRequest();
        movieRequest.setGenre("");
        movieRequest.setCount(0);

        mockMvc.perform(
                post("/api/movies/recommendInGenre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieRequest))
                )
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string(containsString("Жанр не должен быть пустым")))
                        .andExpect(content().string(containsString("Минимум один фильм")));
    }

    @Test
    void shouldReturnRecommendationsWhenValidRequest() throws Exception {
        MovieRecommendationsResponse movieRecommendationsResponse = new MovieRecommendationsResponse(List.of("Фильм 1", "Фильм 2", "Фильм 3"));

       when(deepSeekApiClient.getRecommendations(any(), anyInt()))
               .thenReturn(movieRecommendationsResponse);

       MovieRecommendationRequest request = new MovieRecommendationRequest();
       request.setGenre("Комедия");
       request.setCount(3);

        mockMvc.perform(
                post("/api/movies/recommendInGenre")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recommendations", hasSize(3))) // Проверяем количество
                .andExpect(jsonPath("$.recommendations[0]", is("Фильм 1")))
                .andExpect(jsonPath("$.recommendations[1]", is("Фильм 2")))
                .andExpect(jsonPath("$.recommendations[2]", is("Фильм 3")));

        verify(deepSeekApiClient, times(1)).getRecommendations(any(), anyInt());
    }
}
