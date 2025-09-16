package com.example.demo.service;

import com.example.demo.config.RecommendationProperties;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserMovieRatingRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.User;
import com.example.demo.repository.entity.UserMovieRating;
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

    //Сохранить или обновить оценку фильма.
    @Transactional
    public UserMovieRating rateMovieEntity(Long userId, String genre, String emotion, String title, BigDecimal rating) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

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

    //Получить все оценки пользователя.
    public List<UserMovieRating> getUserRatings(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return userMovieRatingRepository.findAllByUser(user);
    }

    //Рекомендации по жанру с исключением уже оценённых фильмов.
    public List<MovieRecommendationDto> getFilteredByGenre(Long userId, String genre, int count) {
        return getFilteredMovies(userId, count,
                fetchCount -> movieRecommendationService.getMovieRecommendationsWithExclusions(
                        genre,
                        fetchCount + getUserRatings(userId).size(), // немного больше для фильтрации
                        getUserRatings(userId).stream()
                                .map(UserMovieRating::getMovieTitle)
                                .toList()
                )
        );
    }

    public List<MovieRecommendationDto> getFilteredByEmotion(Long userId, String emotion, int count) {
        return getFilteredMovies(userId, count,
                fetchCount -> movieRecommendationService.getMovieRecommendationsOnEmotionWithExclusions(
                        emotion,
                        fetchCount + getUserRatings(userId).size(),
                        new ArrayList<>()
                )
        );
    }

    // Общий метод фильтрации, чтобы не дублировать код
    private List<MovieRecommendationDto> getFilteredMovies(
            Long userId,
            int count,
            java.util.function.Function<Integer, List<MovieRecommendationDto>> fetchFunction
    ) {
        Set<String> excluded = getUserRatings(userId).stream()
                .map(UserMovieRating::getMovieTitle)
                .filter(Objects::nonNull)
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        // Получаем рекомендации с небольшим запасом, чтобы хватило после фильтрации
        List<MovieRecommendationDto> recs = fetchFunction.apply(count);

        // Фильтруем уже оценённые и оставляем ровно count фильмов
        return recs.stream()
                .filter(r -> r.getTitle() != null && !excluded.contains(r.getTitle().trim().toLowerCase()))
                .limit(count)
                .toList();
    }
}