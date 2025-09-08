package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationWithPhraseTranslationsDTO;
import com.antonio.apprendrebackend.service.exception.PhraseNotFoundException;
import com.antonio.apprendrebackend.service.exception.WordTranslationNotFoundException;
import com.antonio.apprendrebackend.service.model.Language;

import java.util.List;

public interface WordTranslationService {
    /**
     * Get All WordTranslation and their Phrases associated given a deck
     *
     * @param deckId
     * @return List<WordTranslationWithPhrasesDTO> of a deck
     * @throws WordTranslationNotFoundException if not exist any WordTranslation of the deck
     */
    List<WordTranslationWithPhraseTranslationsDTO> getAllWordTranslationsWithPhrasesByDeck(Integer deckId);

    /**
     * Get the page pageNumber of WordTranslationDTO with pageSize elements of a language
     *
     * @param pageNumber
     * @param pageSize
     * @param language
     * @return List<WordTranslationDTO>
     * @throws WordTranslationNotFoundException if not exist any WordTranslation
     */
    List<WordTranslationDTO> getAllWordTranslations(Integer pageNumber, Integer pageSize, Language language);
}