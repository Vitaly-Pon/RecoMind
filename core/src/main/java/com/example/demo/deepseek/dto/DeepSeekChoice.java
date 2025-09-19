package com.example.demo.deepseek.dto;

import com.example.demo.controller.response.ChatMessageResponse;
import lombok.Data;

@Data
public class DeepSeekChoice {
    private ChatMessageResponse message;
    private String finish_reason;
}
