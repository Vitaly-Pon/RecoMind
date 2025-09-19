package com.example.demo.deepseek;

import lombok.Data;

import java.util.List;

@Data
public class DeepSeekMoviesResult {
    private List<DeepSeekMovieDto> movies;

    public List<DeepSeekMovieDto> getMovies() {
        return movies;
    }
}
