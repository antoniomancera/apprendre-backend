package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.AttemptResultDTO;
import com.antonio.apprendrebackend.service.dto.WordPhraseTranslationDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.model.UserInfo;
import org.springframework.web.bind.annotation.RequestParam;

public interface WordPhraseTranslationService {

    /**
     * Return a Random WordTranslation depending on an optional deck
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
     * @throws DeckUserWordPhraseTranslationNotFoundException if not exist anyone
     */
    AttemptResultDTO attemptsWordPhraseTranslation(UserInfo userInfo, Integer wordPhraseId, Integer deckId, String attempt);
}
