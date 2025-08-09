package com.example.demo.deepseek.dto;

import lombok.Data;

import java.util.List;

@Data
public class DeepSeekChatResponse {
    private String id;
    private List<RecommendationChoice> choices;
}
