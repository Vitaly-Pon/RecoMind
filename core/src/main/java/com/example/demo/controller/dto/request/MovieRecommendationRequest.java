package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class MovieRecommendationRequest extends BaseMovieRequest{
    @NotBlank(message = "Жанр не должен быть пустым")
    private String genre;
}
