package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.PhraseWithWordTranslationsDTO;
import com.antonio.apprendrebackend.service.exception.PhraseNotFoundException;
import com.antonio.apprendrebackend.service.model.Phrase;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PhraseService {
    List<Phrase> findPhrasesByWordTranslation(WordTranslation wordTranslation);

    /**
     * Get All Phrases and their WordTranslation of a deck
     *
     * @param deckId
     * @return List<PhraseWithWordTranslationsDTO> of a deck
     * @throws PhraseNotFoundException if not exist any Phrase of the deck
     */
    List<PhraseWithWordTranslationsDTO> getAllPhrasesWithWordTranslationsByDeck(Integer deckId);

    /**
     * Get all phrases associated to a WordTranslation of a Deck
     *
     * @param deckId
     * @param wordTranslationId
     * @return List<Phrase>
     */
    List<Phrase> findPhrasesByDeckIdAndWordTranslationId(Integer deckId, Integer wordTranslationId);
}
