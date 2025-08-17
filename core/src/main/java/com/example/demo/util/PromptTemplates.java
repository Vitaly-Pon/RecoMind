package com.example.demo.util;

public class PromptTemplates {


    private static final String JSON_EXAMPLE =
            "{\"movies\": [{\"title\": " +
                    "\"Название\", " +
                    "\"year\": 2020, " +
                    "\"director\": \"Режиссер\", " +
                    "\"imdb_rating\": 8.15, " +
                    "\"kinopoisk_rating\": 8.15, " +
                    "\"description\": \"Описание\"}]}";

    private static final String GENRE_TEMPLATE =
            "Ответь строго в JSON формате. Пример: " + JSON_EXAMPLE + ". " +
                    "Порекомендуй ровно %d лучших фильмов в жанре '%s'. " +
                    "Укажи реальные рейтинги с IMDB и Кинопоиска. " +
                    "Описание каждого фильма должно быть коротким в 1–2 предложения.";

    private static final String EMOTION_TEMPLATE =
            "Ответь строго в JSON формате. Пример: " + JSON_EXAMPLE + ". " +
                    "Порекомендуй ровно %d фильмов с настроением '%s'. " +
                    "Укажи реальные рейтинги с IMDB и Кинопоиска. " +
                    "Описание каждого фильма должно быть коротким в 1–2 предложения.";

    private PromptTemplates() {
    }

    public static String generateGenrePrompt(String genre, int count) {
        return String.format(GENRE_TEMPLATE, count, genre);
    }

    public static String generateEmotionPrompt(String emotion, int count) {
        return String.format(EMOTION_TEMPLATE, count, emotion);
    }
}