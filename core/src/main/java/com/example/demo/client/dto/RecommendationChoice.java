package com.example.demo.client.dto;

import com.example.demo.controller.dto.response.ChatMessageResponse;
import lombok.Data;

@Data
public class RecommendationChoice {
    private ChatMessageResponse message;
    private String finish_reason;
}
