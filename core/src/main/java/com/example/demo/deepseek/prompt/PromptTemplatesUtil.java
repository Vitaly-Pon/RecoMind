package com.example.demo.deepseek.prompt;

import java.util.List;

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

    public static String generateGenrePrompt(String genre, int count, List<String> excluded) {
        String genrePhase = String.format(GENRE_TEMPLATE, genre);
        String excludePhase = buildExcludeClause(excluded);
        return String.format(BASE_TEMPLATE, count, genrePhase) + excludePhase;
    }

    public static String generateEmotionPrompt(String emotion, int count, List<String> excluded) {
        String emotionPhase = String.format(EMOTION_TEMPLATE, emotion);
        String excludePhase = buildExcludeClause(excluded);
        return String.format(BASE_TEMPLATE, count, emotionPhase) + excludePhase;
    }

    private static String buildExcludeClause(List<String> excluded) {
        if (excluded == null || excluded.isEmpty()) {
            return "";
        }
        return " Не включай в список следующие фильмы: " + String.join(", ", excluded) + ".";
    }
}
