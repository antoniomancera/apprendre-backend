package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.DailyStats;
import com.antonio.apprendrebackend.service.model.WordTranslationHistorial;

import java.util.List;

public interface WordTranslationHistorialService {
    List<WordTranslationHistorial> getWordTranslationHistorialLastWeek();

    WordTranslationHistorial getLastWordTranslationHistorial();
}
