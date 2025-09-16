package com.example.demo.controller.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMovieRatingRequest {
    @NotNull(message = "userId is required")
    private Long userId;

    private String genre;
    private String emotion;

    @NotBlank(message = "title must not be blank")
    private String title;

    @NotNull(message = "rating is required")
    @Min(value = 1, message = "Минимум 1")
    @Max(value = 10, message = "Максимум 10")
    private Integer rating;
}

