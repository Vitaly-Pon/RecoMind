package com.example.demo.controller.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class DeepSeekChatResponse {
    private String id;
    private List<RecommendationChoice> choices;
}
