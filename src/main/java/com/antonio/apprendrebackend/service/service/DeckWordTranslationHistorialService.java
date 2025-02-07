package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.DeckWordTranslationHistorialDTO;
import com.antonio.apprendrebackend.service.exception.DeckWordTranslationHistorialNotFoundException;
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

    /**
     * Return all DeckWordTranslationHistorial for a day
     *
     * @param dayMillis
     * @return List<DeckWordTranslationHistorialDTO>
     * @throws DeckWordTranslationHistorialNotFoundException if not exist any DeckWordTranslationHistorial the dayMillis day
     */
    List<DeckWordTranslationHistorialDTO> getDeckWordTranslationHistorialByDayMillis(Long dayMillis);
}
