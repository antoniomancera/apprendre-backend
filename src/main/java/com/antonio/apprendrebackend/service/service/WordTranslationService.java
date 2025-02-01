package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.PhraseWithWordTranslationsDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationWithPhrasesDTO;
import com.antonio.apprendrebackend.service.exception.PhraseNotFoundException;

import java.util.List;

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

    /**
     * Get All WordTranslation and their Phrases associated given a deck
     *
     * @param deckId
     * @return List<WordTranslationWithPhrasesDTO> of a deck
     * @throws PhraseNotFoundException if not exist any WordTranslation of the deck
     */
    List<WordTranslationWithPhrasesDTO> getAllWordTranslationsWithPhrasesByDeck(Integer deckId);
}