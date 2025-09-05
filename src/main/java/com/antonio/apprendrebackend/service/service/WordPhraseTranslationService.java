package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.WordPhraseTranslationDTO;
import com.antonio.apprendrebackend.service.exception.WordPhraseTranslationNotFoundException;
import com.antonio.apprendrebackend.service.model.*;

import java.util.List;

public interface WordPhraseTranslationService {


    /**
     * Get all PhraseTranslation associated to a WordTranslation of a Deck
     *
     * @param deckId
     * @param wordTranslationId
     * @return List<Phrase>
     */
    List<PhraseTranslation> getPhrasesByDeckIdAndWordTranslationId(Integer deckId, Integer wordTranslationId);

    /**
     * Get all WordTranslation associated to a phraseTranslation of a Deck
     *
     * @param deckId
     * @param phraseTranslationId
     * @return List<WordTranslation>
     */
    List<WordTranslation> getWordTranslationsByDeckIdPhraseTranslationId(Integer deckId, Integer phraseTranslationId);

    /**
     * Get all WordTranslation associated to a phraseTranslation of a Deck
     *
     * @param senseIds
     * @param course
     * @return List<WordPhraseTranslationDTO>
     */
    List<WordPhraseTranslationDTO> getAllCurrentCourseWordPhraseTranslationByWordSenseIds(List<Integer> senseIds, Course course);

    /**
     * Get a WordPhraseTranslation given their id
     *
     * @param wordPhraseTranslationId
     * @return WordPhraseTranslation
     * @throws WordPhraseTranslationNotFoundException
     */
    WordPhraseTranslation getWordPhraseTranslationById(Integer wordPhraseTranslationId);
}
