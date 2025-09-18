package com.example.demo.service;

import com.example.demo.config.RecommendationProperties;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.UserMovieRatingRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Movie;
import com.example.demo.repository.entity.User;
import com.example.demo.repository.entity.MovieRating;
import com.example.demo.service.dto.MovieRecommendationDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMovieRatingServiceImp implements  UserMovieRatingService{

    private final UserMovieRatingRepository userMovieRatingRepository;
    private final UserRepository userRepository;
    private final MovieRecommendationService movieRecommendationService;
    private final RecommendationProperties recommendationProperties;
    private final MovieRepository movieRepository;

    @Transactional
    public MovieRating rateMovie(Long userId, String title, BigDecimal rating) {
        // Найти пользователя
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // Найти фильм по названию (или создать новый, если не существует)
        Movie movie = movieRepository.findByMovieTitle(title)
                .orElseGet(() -> movieRepository.save(
                        Movie.builder()
                                .movieTitle(title)
                                .build()
                ));

        // Проверяем, есть ли уже оценка этого пользователя для этого фильма
        Optional<MovieRating> existing = userMovieRatingRepository.findByUserAndMovie(user, movie);

        MovieRating entity;
        if (existing.isPresent()) {
            // Обновляем существующую оценку
            entity = existing.get();
            entity.setRating(rating);
        } else {
            // Создаём новую оценку
            entity = MovieRating.builder()
                    .user(user)
                    .movie(movie)
                    .rating(rating)
                    .build();
        }

        return userMovieRatingRepository.save(entity);
    }

    //Получить все оценки пользователя
    public List<MovieRating> getUserRatings(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return userMovieRatingRepository.findAllByUser(user);
    }

    //формирование списка исключений из уже оценённых фильмов
    public List<MovieRecommendationDto> getFilteredByGenre(Long userId, String genre, int count) {
        Set<String> excluded = buildExcluded(userId, recommendationProperties.getMaxFetchLimit());

        return movieRecommendationService.getMovieRecommendationsWithExclusions(genre,count, new ArrayList<>(excluded));
    }

    public List<MovieRecommendationDto> getFilteredByEmotion(Long userId, String emotion, int count) {
        Set<String> excluded = buildExcluded(userId, recommendationProperties.getMaxFetchLimit());

        return movieRecommendationService.getMovieRecommendationsOnEmotionWithExclusions(emotion, count, new ArrayList<>(excluded));
    }
    private Set<String> buildExcluded(Long userId, int limit ){
        List<MovieRating> userRatings = getUserRatings(userId);
        List<MovieRating> userRatings10 = userRatings.stream()
                .sorted(Comparator.comparing(MovieRating::getCreatedAt).reversed())
                .limit(limit)
                .toList();

        Set<String> excluded = userRatings10.stream()
                .map(MovieRating::getMovie)
                .filter(Objects::nonNull)
                .map(Movie:: getMovieTitle)
                .filter(Objects:: nonNull)
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
        return excluded;
    }
}