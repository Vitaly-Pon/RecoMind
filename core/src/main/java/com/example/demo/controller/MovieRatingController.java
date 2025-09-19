package com.example.demo.controller;

import com.example.demo.controller.request.UserMovieRatingRequest;
import com.example.demo.controller.response.UserMovieRatingResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/movie")
public interface MovieRatingController {

    @PostMapping("/ratings")
    UserMovieRatingResponse rateMovie(@RequestBody UserMovieRatingRequest request);

}