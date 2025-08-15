package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class MovieRecByEmotionRequest extends BaseMovieRequest{
    @NotBlank(message = "Поле не должно быть пустым")
    private String emotion;
}
