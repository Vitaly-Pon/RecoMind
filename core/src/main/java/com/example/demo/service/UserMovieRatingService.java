package com.example.demo.service;

import com.example.demo.config.RecommendationProperties;
import com.example.demo.repository.UserMovieRatingRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.User;
import com.example.demo.repository.entity.UserMovieRating;
import com.example.demo.service.dto.MovieRecommendationDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMovieRatingService {

    private final UserMovieRatingRepository userMovieRatingRepository;
    private final UserRepository userRepository;
    private final MovieRecommendationService movieRecommendationService;
    private final RecommendationProperties properties;

    /**
     * Сохранить или обновить оценку фильма.
     */
    @Transactional
    public UserMovieRating rateMovieEntity(Long userId, String genre, String emotion, String title, int rating) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + userId));

        Optional<UserMovieRating> existing = userMovieRatingRepository.findByUserAndMovieTitle(user, title);

        UserMovieRating entity;
        if (existing.isPresent()) {
            entity = existing.get();
            entity.setRating(rating);
        } else {
            entity = UserMovieRating.builder()
                    .user(user)
                    .genre(genre)
                    .emotion(emotion)
                    .movieTitle(title)
                    .rating(rating)
                    .build();
        }

        return userMovieRatingRepository.save(entity);
    }

    /**
     * Получить все оценки пользователя.
     */
    public List<UserMovieRating> getUserRatings(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + userId));

        return userMovieRatingRepository.findAllByUser(user);
    }

    /**
     * Рекомендации по жанру с исключением уже оценённых фильмов.
     */
    public List<MovieRecommendationDto> getFilteredByGenre(Long userId, String genre, int count) {
        List<String> excluded = getUserRatings(userId).stream()
                .map(UserMovieRating::getMovieTitle)
                .toList();

        List<MovieRecommendationDto> recs = movieRecommendationService
                .getMovieRecommendationsWithExclusions(genre, count + excluded.size(), excluded);

        return recs.stream()
                .filter(r -> !excluded.contains(r.getTitle()))
                .limit(count)
                .toList();
    }

    /**
     * Рекомендации по эмоции с исключением уже оценённых фильмов.
     */
    public List<MovieRecommendationDto> getFilteredByEmotion(Long userId, String emotion, int count) {
        // 1) собрать excluded как Set с нормализацией
        Set<String> excluded = getUserRatings(userId).stream()
                .map(UserMovieRating::getMovieTitle)
                .filter(Objects::nonNull)
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        // 2) сколько реально нужно запросить у recommendation service
        int desiredFetch = Math.min(properties.getMaxFetchLimit(), count + excluded.size());

        // 3) запросить рекомендации (передаём список exclusions, если сервис поддерживает)
        List<MovieRecommendationDto> recs = movieRecommendationService
                .getMovieRecommendationsOnEmotionWithExclusions(emotion, desiredFetch, new ArrayList<>(excluded));

        // 4) фильтрация + нормализация + уникальность + ограничение
        List<MovieRecommendationDto> result = recs.stream()
                .filter(r -> {
                    String title = r.getTitle();
                    return title != null && !excluded.contains(title.trim().toLowerCase());
                })
                .filter(distinctByKey(r -> r.getTitle().trim().toLowerCase()))
                .limit(count)
                .toList();

        return result;
    }
    // helper
    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
//TODO возвращать свои сущности а не списки