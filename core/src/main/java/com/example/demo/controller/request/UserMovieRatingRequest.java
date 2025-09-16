package com.example.demo.controller.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
    @DecimalMin(value = "1.0", message = "Минимум 1.0")
    @DecimalMax(value = "10.0", message = "Максимум 10.0")
    @Digits(integer = 2, fraction = 1, message = "Можно указать максимум одну цифру после запятой")
    private BigDecimal rating;
}

