package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.PhraseNotFoundException;
import com.antonio.apprendrebackend.service.model.WordTranslation;

import java.util.List;

public interface DeckWordTranslationService {
    /**
     * Get All Phrases and their WordTranslation of a deck
     *
     * @param deckId
     * @return A List<PhraseWithWordTranslationsDTO>>
     */
    List<WordTranslation> getWordTranslationsByPhraseIdAndDeckId(Integer phraseId, Integer deckId);
}
