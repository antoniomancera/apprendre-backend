package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.exception.DeckUserWordPhraseTranslationNotFoundException;
import com.antonio.apprendrebackend.service.model.DeckUserWordPhraseTranslation;
import com.antonio.apprendrebackend.service.model.PhraseTranslation;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import com.antonio.apprendrebackend.service.repository.DeckUserWordPhraseTranslationRespository;
import com.antonio.apprendrebackend.service.service.DeckUserWordPhraseTranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DeckUserWordPhraseTranslationServiceImpl implements DeckUserWordPhraseTranslationService {

    @Autowired
    DeckUserWordPhraseTranslationRespository deckUserWordPhraseTranslationRespository;

    /**
     * Return a DeckUserWordPhraseTranslation
     *
     * @param userId
     * @return DeckUserWordPhraseTranslation
     */
    @Override
    public DeckUserWordPhraseTranslation getRandomUserDeckWordPhraseTranslationWithByUser(Integer userId) {
        Optional<DeckUserWordPhraseTranslation> deckUserWordPhraseTranslation = deckUserWordPhraseTranslationRespository.findRandomUserDeckWordPhraseTranslationWithByUser(userId);
        if (deckUserWordPhraseTranslation.isEmpty()) {
            throw new DeckUserWordPhraseTranslationNotFoundException(String.format("Word not found for deck"));
        }
        return deckUserWordPhraseTranslation.get();

    }

    /**
     * Return a WordTranslation of a deck
     *
     * @param deckId
     * @param userId
     * @return DeckUserWordPhraseTranslation
     */
    @Override
    public DeckUserWordPhraseTranslation getRandomUserDeckWordPhraseTranslationWithByDeckAndUser(Integer deckId, Integer userId) {
        Optional<DeckUserWordPhraseTranslation> deckUserWordPhraseTranslation = deckUserWordPhraseTranslationRespository.findRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId, userId);
        if (deckUserWordPhraseTranslation.isEmpty()) {
            throw new DeckUserWordPhraseTranslationNotFoundException(String.format("Word not found for deck", deckId));
        }
        return deckUserWordPhraseTranslation.get();
    }

    /**
     * Get DeckUserWordPhraseTranslation given a DeckUser and a WordPhraseTranslation
     *
     * @param deckId
     * @param wordPhraseTranslationId
     * @return a DeckUserWordPhraseTranslation
     */
    @Override
    public DeckUserWordPhraseTranslation getByDeckUserIdAndWordPhraseTranslationId(Integer deckId, Integer wordPhraseTranslationId) {
        return deckUserWordPhraseTranslationRespository
                .findByDeckUserIdAndWordPhraseTranslationId(deckId, wordPhraseTranslationId)
                .orElseThrow(() -> new DeckUserWordPhraseTranslationNotFoundException(
                        String.format(
                                "DeckUserWordPhraseTranslation not found for deckId %d and wordPhraseTranslationId %d",
                                deckId,
                                wordPhraseTranslationId
                        )
                ));
    }

    /**
     * Return all WordTranslation of a deckUser
     *
     * @param deckId
     * @return List<WordTranslation>
     */
    @Override
    public List<WordTranslation> getWordTranslationsByDeckId(Integer deckId) {
        return deckUserWordPhraseTranslationRespository.findWordTranslationsByDeckId(deckId);
    }

    /**
     * Return all PhraseTranslation of a deckUser
     *
     * @param deckId
     * @return List<PhraseTranslation>
     */
    @Override
    public List<PhraseTranslation> getPhraseTranslationsByDeckId(Integer deckId) {
        return deckUserWordPhraseTranslationRespository.findPhraseTranslationsByDeckId(deckId);
    }
}
