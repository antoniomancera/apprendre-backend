package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.DeckWordPhraseTranslation;
import com.antonio.apprendrebackend.service.model.PhraseTranslation;
import com.antonio.apprendrebackend.service.model.WordTranslation;

import java.util.List;

public interface DeckWordPhraseTranslationService {
    /**
     * Return a DeckWordPhraseTranslation
     *
     * @param userId
     * @return DeckWordPhraseTranslation
     */
    DeckWordPhraseTranslation getRandomUserDeckWordPhraseTranslationWithByUser(Integer userId);


    /**
     * Return a WordTranslation of a deck
     *
     * @param deckId
     * @param userId
     * @return DeckWordPhraseTranslation
     */
    DeckWordPhraseTranslation getRandomUserDeckWordPhraseTranslationWithByDeckAndUser(Integer deckId, Integer userId);

    /**
     * Get DeckWordPhraseTranslation given a Deck and a WordPhraseTranslation
     *
     * @param deckId
     * @param wordPhraseTranslationId
     * @return a DeckWordPhraseTranslation
     */
    DeckWordPhraseTranslation getByDeckIdAndWordPhraseTranslationId(Integer deckId, Integer wordPhraseTranslationId);

    /**
     * Return all WordTranslation of a deck
     *
     * @param deckId
     * @return List<WordTranslation>
     */
    List<WordTranslation> getWordTranslationsByDeckId(Integer deckId);

    /**
     * Return all PhraseTranslation of a deck
     *
     * @param deckId
     * @return List<PhraseTranslation>
     */
    List<PhraseTranslation> getPhraseTranslationsByDeckId(Integer deckId);
}


