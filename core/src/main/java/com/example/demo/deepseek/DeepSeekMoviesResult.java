package com.example.demo.deepseek;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DeepSeekMoviesResult {

        @JsonProperty("movies")
        private List<DeepSeekMovieDto> movies;
        public List<DeepSeekMovieDto> getMovies() {
            return movies;
        }
}
