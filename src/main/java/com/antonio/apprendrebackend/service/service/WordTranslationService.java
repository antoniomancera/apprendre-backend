package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;

public interface WordTranslationService {
    /**
     * Return a Random WordTranslation depending on an optional deck
     *
     * @param deckId
     * @return
     */
    WordTranslationDTO getRandomWordTranslation(Integer deckId);

    /**
     * Update phrase, wordTranslate and WordTranslateHistorial depending on the result of the attemps, besides returns a WordTranslation
     *
     * @param wordId
     * @param phraseId
     * @param success
     * @param deckId
     * @return
     */
    WordTranslationDTO attemptsWordTranslation(int wordId, int phraseId, boolean success, Integer deckId);
}