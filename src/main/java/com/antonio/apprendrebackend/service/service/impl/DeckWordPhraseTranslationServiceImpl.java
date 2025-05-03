package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.exception.DeckWordPhraseTranslationNotFoundException;
import com.antonio.apprendrebackend.service.model.DeckWordPhraseTranslation;
import com.antonio.apprendrebackend.service.model.PhraseTranslation;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import com.antonio.apprendrebackend.service.repository.DeckWordPhraseTranslationRespository;
import com.antonio.apprendrebackend.service.service.DeckWordPhraseTranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DeckWordPhraseTranslationServiceImpl implements DeckWordPhraseTranslationService {

    @Autowired
    DeckWordPhraseTranslationRespository deckWordPhraseTranslationRespository;

    /**
     * Return a DeckWordPhraseTranslation
     *
     * @param userId
     * @return DeckWordPhraseTranslation
     */
    @Override
    public DeckWordPhraseTranslation getRandomUserDeckWordPhraseTranslationWithByUser(Integer userId) {
        Optional<DeckWordPhraseTranslation> deckWordPhraseTranslation = deckWordPhraseTranslationRespository.findRandomUserDeckWordPhraseTranslationWithByUser(userId);
        if (deckWordPhraseTranslation.isEmpty()) {
            throw new DeckWordPhraseTranslationNotFoundException(String.format("Word not found for deck"));
        }
        return deckWordPhraseTranslation.get();

    }

    /**
     * Return a WordTranslation of a deck
     *
     * @param deckId
     * @param userId
     * @return DeckWordPhraseTranslation
     */
    @Override
    public DeckWordPhraseTranslation getRandomUserDeckWordPhraseTranslationWithByDeckAndUser(Integer deckId, Integer userId) {
        Optional<DeckWordPhraseTranslation> deckWordPhraseTranslation = deckWordPhraseTranslationRespository.findRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId, userId);
        if (deckWordPhraseTranslation.isEmpty()) {
            throw new DeckWordPhraseTranslationNotFoundException(String.format("Word not found for deck", deckId));
        }
        return deckWordPhraseTranslation.get();
    }

    /**
     * Get DeckWordPhraseTranslation given a Deck and a WordPhraseTranslation
     *
     * @param deckId
     * @param wordPhraseTranslationId
     * @return a DeckWordPhraseTranslation
     */
    @Override
    public DeckWordPhraseTranslation getByDeckIdAndWordPhraseTranslationId(Integer deckId, Integer wordPhraseTranslationId) {
        return deckWordPhraseTranslationRespository
                .findByDeckIdAndWordPhraseTranslationId(deckId, wordPhraseTranslationId)
                .orElseThrow(() -> new DeckWordPhraseTranslationNotFoundException(
                        String.format(
                                "DeckWordPhraseTranslation not found for deckId %d and wordPhraseTranslationId %d",
                                deckId,
                                wordPhraseTranslationId
                        )
                ));
    }

    /**
     * Return all WordTranslation of a deck
     *
     * @param deckId
     * @return List<WordTranslation>
     */
    @Override
    public List<WordTranslation> getWordTranslationsByDeckId(Integer deckId) {
        return deckWordPhraseTranslationRespository.findWordTranslationsByDeckId(deckId);
    }

    /**
     * Return all PhraseTranslation of a deck
     *
     * @param deckId
     * @return List<PhraseTranslation>
     */
    @Override
    public List<PhraseTranslation> getPhraseTranslationsByDeckId(Integer deckId) {
        return deckWordPhraseTranslationRespository.findPhraseTranslationsByDeckId(deckId);
    }
}
