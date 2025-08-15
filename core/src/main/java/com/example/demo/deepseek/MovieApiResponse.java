package com.example.demo.deepseek;

import lombok.Data;

import java.util.List;
@Data
public class MovieApiResponse {
    private List<MovieInfo> movies;
}
