package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.PhraseTranslationDTO;
import com.antonio.apprendrebackend.service.dto.PhraseTranslationWithWordTranslationsDTO;
import com.antonio.apprendrebackend.service.exception.PhraseNotFoundException;
import com.antonio.apprendrebackend.service.model.Language;

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
     * Get the page pageNumber of PhraseTranslationDTO with pageSize elements of a target language
     *
     * @param pageNumber
     * @param pageSize
     * @param language
     * @return List<PhraseDTO>
     * @throws PhraseNotFoundException if not exist any Phrase
     */
    List<PhraseTranslationDTO> getAllTargetLanguagePhrases(Integer pageNumber, Integer pageSize, Language language);
}
