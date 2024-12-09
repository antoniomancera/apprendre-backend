package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.DailyStats;
import com.antonio.apprendrebackend.service.model.WordTranslationHistorial;

import java.util.List;
import java.util.Optional;

public interface WordTranslationHistorialService {
    Optional<List<WordTranslationHistorial>> getWordTranslationHistorialLastWeek();

    WordTranslationHistorial getLastWordTranslationHistorial();
}
