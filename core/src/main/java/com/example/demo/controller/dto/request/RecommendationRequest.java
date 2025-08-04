package com.example.demo.controller.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class RecommendationRequest {
    private String model = "deepseek-chat";  // Модель по умолчанию
    private List<RecommendationMessage> messages;
    private int max_tokens = 500; // Ограничение длины ответа

}
