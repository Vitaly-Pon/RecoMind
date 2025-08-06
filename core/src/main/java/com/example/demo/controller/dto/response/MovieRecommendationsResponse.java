package com.example.demo.controller.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class MovieRecommendationsResponse {
    private List<String> recommendations;
}
