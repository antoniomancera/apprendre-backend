package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.WordPhraseTranslationDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.model.UserInfo;

public interface WordPhraseTranslationService {

    /**
     * Return a Random WordTranslation depending on an optional deck
     *
     * @param deckId
     * @return WordPhraseTranslationDTO
     */
    WordPhraseTranslationDTO getRandomWordPhraseTranslation(UserInfo userInfo, Integer deckId);

    /**
     * Update phrase, wordTranslate and WordTranslateHistorial depending on the result of the attemps, besides returns a WordTranslation
     *
     * @param wordId
     * @param phraseId
     * @param success
     * @param deckId
     * @return
     */
    WordTranslationDTO attemptsWordPhraseTranslation(int wordId, int phraseId, boolean success, Integer deckId);
}
