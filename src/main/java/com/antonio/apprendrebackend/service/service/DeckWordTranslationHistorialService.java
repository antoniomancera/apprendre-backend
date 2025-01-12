package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.DeckWordTranslationHistorial;

import java.util.List;
import java.util.Optional;

public interface DeckWordTranslationHistorialService {
    /**
     * Get the historial of an Deck in the last week
     *
     * @return
     */
    Optional<List<DeckWordTranslationHistorial>> getWordTranslationHistorialLastWeek();

    /**
     * Get the last DeckWordTranslation
     *
     * @return
     */
    Optional<DeckWordTranslationHistorial> getLastWordTranslationHistorial();
}
