package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MovieRecommendationRequest {
    @NotBlank(message = "Жанр не должен быть пустым")
    private String genre;

    @Min(value = 1, message = "Минимум один фильм")
    @Max(value = 10, message = "Максимум десять фильмов")
    private int count = 5;
}
