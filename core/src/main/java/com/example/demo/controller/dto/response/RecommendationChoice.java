package com.example.demo.controller.dto.response;

import lombok.Data;

@Data
public class RecommendationChoice {
    private RecommendationMessage message;
    private String finish_reason;
}
