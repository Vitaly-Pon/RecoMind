package com.example.demo.service;

import com.example.demo.config.RecommendationProperties;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserMovieRatingRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.User;
import com.example.demo.repository.entity.MovieRating;
import com.example.demo.service.dto.MovieRecommendationDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMovieRatingService {

    private final UserMovieRatingRepository userMovieRatingRepository;
    private final UserRepository userRepository;
    private final MovieRecommendationService movieRecommendationService;
    private final RecommendationProperties recommendationProperties;

    //Сохранить или обновить оценку фильма.
    @Transactional
    public MovieRating rateMovie(Long userId, String genre, String emotion, String title, BigDecimal rating) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Optional<MovieRating> existing = userMovieRatingRepository.findByUserAndMovieTitle(user, title);

        MovieRating entity;
        if (existing.isPresent()) {
            entity = existing.get();
            entity.setRating(rating);
        } else {
            entity = MovieRating.builder()
                    .user(user)
                    .genre(genre)
                    .emotion(emotion)
                    .movieTitle(title)
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

    //Рекомендации по жанру с исключением уже оценённых фильмов
    public List<MovieRecommendationDto> getFilteredByGenre(Long userId, String genre, int count) {
        List<MovieRating> userRatings = getUserRatings(userId);
        Set<String> excluded = userRatings.stream()
                .map(MovieRating::getMovieTitle)
                .filter(Objects::nonNull)
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        return getFilteredWithDynamicFetch(
                count,
                fetchCount -> movieRecommendationService.getMovieRecommendationsWithExclusions(
                        genre,
                        fetchCount,
                        new ArrayList<>(excluded)
                ),
                excluded
        );
    }

    public List<MovieRecommendationDto> getFilteredByEmotion(Long userId, String emotion, int count) {
        List<MovieRating> userRatings = getUserRatings(userId);
        Set<String> excluded = userRatings.stream()
                .map(MovieRating::getMovieTitle)
                .filter(Objects::nonNull)
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        return getFilteredWithDynamicFetch(
                count,
                fetchCount -> movieRecommendationService.getMovieRecommendationsOnEmotionWithExclusions(
                        emotion,
                        fetchCount,
                        new ArrayList<>(excluded)
                ),
                excluded
        );
    }

    private List<MovieRecommendationDto> getFilteredWithDynamicFetch(
            int count,
            java.util.function.Function<Integer, List<MovieRecommendationDto>> fetchFunction,
            Set<String> excluded
    ) {
        List<MovieRecommendationDto> result = new ArrayList<>();
        int remaining = count; // сколько нужно добавить
        int maxExtraPerRequest = recommendationProperties.getMaxFetchLimit();
        int maxIterations = 3; // максимум 3 дозагрузки
        int iteration = 0; //счетчик

        while (remaining > 0 && iteration < maxIterations) {
            int fetchCount = remaining + maxExtraPerRequest;
            List<MovieRecommendationDto> fetched = fetchFunction.apply(fetchCount);

            // фильтруем уже добавленные и оценённые фильмы
            List<MovieRecommendationDto> filtered = fetched.stream()
                    .filter(r -> r.getTitle() != null)
                    .filter(r -> !excluded.contains(r.getTitle().trim().toLowerCase()))
                    .filter(r -> result.stream()
                            .noneMatch(existing -> existing.getTitle().equalsIgnoreCase(r.getTitle())))
                    .toList();

            result.addAll(filtered);

            remaining = count - result.size();
            iteration++;
        }

        //  ровно count фильмов
        if (result.size() > count) {
            return result.subList(0, count);
        }

        return result;
    }
}