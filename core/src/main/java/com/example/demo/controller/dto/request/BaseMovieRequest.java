package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public abstract class BaseMovieRequest {

    @Min(value = 1, message = "Минимум один фильм")
    @Max(value = 10, message = "Максимум десять фильмов")
    private Integer count = 5;

}
