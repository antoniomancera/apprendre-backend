package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.AttemptResultDTO;
import com.antonio.apprendrebackend.service.dto.WordPhraseTranslationDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.model.PhraseTranslation;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface WordPhraseTranslationService {

    /**
     * Return a Random WordPhraseTranslationDTO depending on an optional deck
     *
     * @param deckId
     * @return WordPhraseTranslationDTO
     */
    WordPhraseTranslationDTO getRandomWordPhraseTranslation(UserInfo userInfo, Integer deckId);

    /**
     * Attempt a WordPhraseTranslation, will add an userHistorial depending on the result of the attemps, besides returns a WordTranslation
     * in case of success
     *
     * @param wordPhraseId
     * @param deckId
     * @param attempt
     * @return AttemptResultDTO with result and new WordPhraseTranslation in case of success
     * @throws DeckWordPhraseTranslationNotFoundException if not exist anyone
     */
    AttemptResultDTO attemptsWordPhraseTranslation(UserInfo userInfo, Integer wordPhraseId, Integer deckId, String attempt);

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
}
