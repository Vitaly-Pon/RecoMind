package com.example.demo.controller.dto.response;

import lombok.Data;

@Data
public class RecommendationChoice {
    private ChatMessageResponse message;
    private String finish_reason;
}
