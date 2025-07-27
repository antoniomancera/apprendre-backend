package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.AttemptResultDTO;
import com.antonio.apprendrebackend.service.dto.WordPhraseTranslationDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.exception.WordPhraseTranslationNotFoundException;
import com.antonio.apprendrebackend.service.model.PhraseTranslation;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.model.WordPhraseTranslation;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

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
     * @return List<WordTranslation>
     */
    List<WordPhraseTranslationDTO> getAllWordPhraseTranslationByWordSense(List<Integer> senseIds);

    /**
     * Get a WordPhraseTranslation given their id
     *
     * @param wordPhraseTranslationId
     * @return WordPhraseTranslation
     * @throws WordPhraseTranslationNotFoundException
     */
    WordPhraseTranslation getWordPhraseTranslationById(Integer wordPhraseTranslationId);
}
