package com.example.demo.controller;

import com.example.demo.servise.DeepSeekService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
public class DeepSeekController {
    private final DeepSeekService deepSeekService;

    public DeepSeekController(DeepSeekService deepSeekService) {
        this.deepSeekService = deepSeekService;
    }

    @PostMapping("/recommend")
    public String getRecommendMovies(@RequestParam String genre, @RequestParam(defaultValue = "5") int count) {
        return deepSeekService.getMovieRecommendations(genre, count);
    }
}
