package com.example.demo.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MovieRecommendationsResponse {
    private List<String> recommendations;
}
