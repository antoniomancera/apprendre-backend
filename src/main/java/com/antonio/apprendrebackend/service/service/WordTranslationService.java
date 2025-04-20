package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationWithPhrasesDTO;
import com.antonio.apprendrebackend.service.exception.PhraseNotFoundException;
import com.antonio.apprendrebackend.service.exception.WordTranslationNotFoundException;

import java.util.List;

public interface WordTranslationService {
    /**
     * Get All WordTranslation and their Phrases associated given a deck
     *
     * @param deckId
     * @return List<WordTranslationWithPhrasesDTO> of a deck
     * @throws PhraseNotFoundException if not exist any WordTranslation of the deck
     */
    List<WordTranslationWithPhrasesDTO> getAllWordTranslationsWithPhrasesByDeck(Integer deckId);

    /**
     * Get the page pageNumber of WordTranslationDTO with pageSize elements
     *
     * @param pageNumber
     * @param pageSize
     * @return List<WordTranslationDTO>
     * @throws WordTranslationNotFoundException if not exist any WordTranslation
     */
    List<WordTranslationDTO> getAllWordTranslations(Integer pageNumber, Integer pageSize);
}