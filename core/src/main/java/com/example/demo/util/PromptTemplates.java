package com.example.demo.util;

public class PromptTemplates {

    private static final String GENRE_TEMPLATE =
            "Порекомендуй ровно %d лучших фильмов в жанре '%s'. " +
            "Формат: Название (Год, режиссер) — описание. Без вводных слов.";

    private static final String EMOTION_TEMPLATE =
            "Порекомендуй ровно %d фильмов с настроением '%s'. " +
                    "Формат: Название (Год, режиссер) — описание. Без вводных слов.";

    private PromptTemplates() {}

    public static  String generateGenrePrompt(String genre, int count){
        return String.format(GENRE_TEMPLATE, count, genre);
    }

    public static String generateEmotionPrompt(String emotion, int count){
        return String.format(EMOTION_TEMPLATE, count, emotion);
    }
}
