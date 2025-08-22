package com.example.demo.util;

public class PromptTemplatesUtil {
    private static final String JSON_EXAMPLE =
            "{\"movies\": [{\"title\": " +
                    "\"Название\", " +
                    "\"year\": 2020, " +
                    "\"director\": \"Режиссер\", " +
                    "\"imdb_rating\": 8.15, " +
                    "\"kinopoisk_rating\": 8.15, " +
                    "\"description\": \"Описание\"}]}";

    private static final String GENRE_TEMPLATE = "хороших в жанре %s. ";

    private static final String EMOTION_TEMPLATE = " с настроением %s. ";

    private static final String BASE_TEMPLATE =
            "Ответь строго в JSON формате. Пример: " + JSON_EXAMPLE + ". " +
            "Порекомендуй ровно %d фильмов %s. " +
            "Укажи реальные рейтинги с IMDB и Кинопоиска. " +
            "Описание каждого фильма должно быть коротким в 1–2 предложения.";

    private PromptTemplatesUtil() {
    }

    public static String generateGenrePrompt(String genre, int count) {
        String genrePhase = String.format(GENRE_TEMPLATE, genre);
        return String.format(BASE_TEMPLATE, count, genrePhase);
    }

    public static String generateEmotionPrompt(String emotion, int count) {
        String emotionPhase = String.format(EMOTION_TEMPLATE, emotion);
        return String.format(BASE_TEMPLATE, count, emotionPhase);
    }
}