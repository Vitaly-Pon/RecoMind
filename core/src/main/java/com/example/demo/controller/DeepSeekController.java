package com.example.demo.controller;

import com.example.demo.serviсe.DeepSeekService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public String recommendMovies(@RequestParam String genrec) {
        return deepSeekService.getMovieRecommendation(genrec);
    }
}
