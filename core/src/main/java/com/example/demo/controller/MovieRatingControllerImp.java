package com.example.demo.controller;

import com.example.demo.controller.request.UserMovieRatingRequest;
import com.example.demo.controller.response.UserMovieRatingResponse;
import com.example.demo.mapper.UserMovieRatingRestMapper;
import com.example.demo.repository.entity.MovieRating;
import com.example.demo.service.UserMovieRatingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class MovieRatingControllerImp implements MovieRatingController {

    private final UserMovieRatingService userMovieRatingService;

    @Override
    public UserMovieRatingResponse rateMovie(@Valid @RequestBody UserMovieRatingRequest request) {
        MovieRating saved = userMovieRatingService.rateMovie(
                request.getUserId(),
                request.getTitle(),
                request.getRating()
        );
        return new UserMovieRatingResponse(UserMovieRatingRestMapper.toDto(saved));
    }
}