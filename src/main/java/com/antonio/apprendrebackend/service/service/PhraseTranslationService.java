package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.PhraseTranslationDTO;
import com.antonio.apprendrebackend.service.dto.PhraseTranslationWithWordTranslationsDTO;
import com.antonio.apprendrebackend.service.exception.PhraseNotFoundException;

import java.util.List;

public interface PhraseTranslationService {

    /**
     * Get All Phrases and their WordTranslation of a deck
     *
     * @param deckId
     * @return List<PhraseTranslationWithWordTranslationsDTO> of a deck
     * @throws PhraseNotFoundException if not exist any Phrase of the deck
     */
    List<PhraseTranslationWithWordTranslationsDTO> getAllPhrasesWithWordTranslationsByDeck(Integer deckId);

    /**
     * Get the page pageNumber of PhraseTranslationDTO with pageSize elements
     *
     * @param pageNumber
     * @param pageSize
     * @return List<PhraseDTO>
     * @throws PhraseNotFoundException if not exist any Phrase
     */
    List<PhraseTranslationDTO> getAllPhrases(Integer pageNumber, Integer pageSize);
}
