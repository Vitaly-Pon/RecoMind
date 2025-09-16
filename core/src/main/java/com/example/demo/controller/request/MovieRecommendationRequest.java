package com.example.demo.controller.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieRecommendationRequest{
    private Long userId;      // null → общие рекомендации
    private String genre;     // можно null
    private String emotion;   // можно null
    @Min(value = 1, message = "Минимум один фильм")
    @Max(value = 10, message = "Максимум десять фильмов")
    private Integer count = 5;

    @AssertTrue(message = "Нужно указать хотя бы genre или emotion")
    public boolean isGenreOrEmotionPresent() {
        return genre != null || emotion != null;
    }
}


