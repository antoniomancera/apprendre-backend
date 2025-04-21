package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.DeckUserWordPhraseTranslationDTO;
import com.antonio.apprendrebackend.service.model.DeckUserWordPhraseTranslation;
import com.antonio.apprendrebackend.service.model.PhraseTranslation;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DeckUserWordPhraseTranslationService {
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

    /**
     * Get DeckUserWordPhraseTranslation given a DeckUser and a WordPhraseTranslation
     *
     * @param deckId
     * @param wordPhraseTranslationId
     * @return a DeckUserWordPhraseTranslation
     */
    DeckUserWordPhraseTranslation getByDeckUserIdAndWordPhraseTranslationId(Integer deckId, Integer wordPhraseTranslationId);

    /**
     * Return all WordTranslation of a deckUser
     *
     * @param deckId
     * @return List<WordTranslation>
     */
    List<WordTranslation> getWordTranslationsByDeckId(Integer deckId);

    /**
     * Return all PhraseTranslation of a deckUser
     *
     * @param deckId
     * @return List<PhraseTranslation>
     */
    List<PhraseTranslation> getPhraseTranslationsByDeckId(Integer deckId);
}


