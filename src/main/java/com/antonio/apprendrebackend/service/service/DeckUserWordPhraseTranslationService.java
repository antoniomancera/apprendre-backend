package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.DeckUserWordPhraseTranslationDTO;
import com.antonio.apprendrebackend.service.model.DeckUserWordPhraseTranslation;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DeckUserWordPhraseTranslationService {


    /**
     * Get All Phrases and their WordTranslation of a deck
     *
     * @param deckId
     * @return A List<PhraseWithWordTranslationsDTO>>
     */
    List<WordTranslation> getWordTranslationsByPhraseIdAndDeckId(Integer phraseId, Integer deckId);


    /**
     * Return a DeckUserWordPhraseTranslation
     *
     * @param userId
     * @return DeckUserWordPhraseTranslation
     */
    DeckUserWordPhraseTranslation getRandomUserDeckWordPhraseTranslationWithByUser(Integer userId);


    /**
     * Return a WordTranslation of a deck
     *
     * @param deckId
     * @param userId
     * @return DeckUserWordPhraseTranslation
     */
    DeckUserWordPhraseTranslation getRandomUserDeckWordPhraseTranslationWithByDeckAndUser(Integer deckId, Integer userId);
}
