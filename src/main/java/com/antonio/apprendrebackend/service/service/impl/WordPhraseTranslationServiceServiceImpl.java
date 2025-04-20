package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.AttemptResultDTO;
import com.antonio.apprendrebackend.service.dto.WordPhraseTranslationDTO;
import com.antonio.apprendrebackend.service.mapper.WordPhraseTranslationMapper;
import com.antonio.apprendrebackend.service.model.DeckUserWordPhraseTranslation;
import com.antonio.apprendrebackend.service.model.UserHistorial;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.service.DeckUserWordPhraseTranslationService;
import com.antonio.apprendrebackend.service.service.UserHistorialService;
import com.antonio.apprendrebackend.service.service.WordPhraseTranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordPhraseTranslationServiceServiceImpl implements WordPhraseTranslationService {

    @Autowired
    DeckUserWordPhraseTranslationService deckUserWordPhraseTranslationService;

    @Autowired
    WordPhraseTranslationMapper wordPhraseTranslationMapper;

    @Autowired
    UserHistorialService userHistorialService;

    /**
     * Return a Random WordTranslation depending on an optional deck
     *
     * @param deckId
     * @return WordPhraseTranslationDTO
     */
    @Override
    public WordPhraseTranslationDTO getRandomWordPhraseTranslation(UserInfo userInfo, Integer deckId) {
        DeckUserWordPhraseTranslation deckWordTranslation;
        if (deckId != null) {
            deckWordTranslation = deckUserWordPhraseTranslationService.getRandomUserDeckWordPhraseTranslationWithByDeckAndUser(userInfo.getId(), deckId);
        } else {
            deckWordTranslation = deckUserWordPhraseTranslationService.getRandomUserDeckWordPhraseTranslationWithByUser(userInfo.getId());
        }
        return wordPhraseTranslationMapper.toDTO(deckWordTranslation.getWordPhraseTranslation());
    }

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
    @Override
    public AttemptResultDTO attemptsWordPhraseTranslation(UserInfo userInfo, Integer wordPhraseId, Integer deckId, String attempt) {
        DeckUserWordPhraseTranslation deckUserWordPhraseTranslation = deckUserWordPhraseTranslationService.getByDeckUserIdAndWordPhraseTranslationId(deckId, wordPhraseId);
        Boolean hasSuccess = deckUserWordPhraseTranslation.getWordPhraseTranslation().getWordTranslation().getWordSenseFr().getWord().getName().equals(attempt);
        updateStats(hasSuccess, deckUserWordPhraseTranslation);
        userHistorialService.postUserHistorial(new UserHistorial(deckUserWordPhraseTranslation, hasSuccess ? 1 : 0, deckId));
        if (hasSuccess) {
            return new AttemptResultDTO(true, getRandomWordPhraseTranslation(userInfo, deckId));
        }
        return new AttemptResultDTO(false, null);
    }

    private static void updateStats(boolean success, DeckUserWordPhraseTranslation deckUserWordPhraseTranslation) {
        deckUserWordPhraseTranslation.setAttempts(deckUserWordPhraseTranslation.getAttempts() + 1);
        if (success) {
            deckUserWordPhraseTranslation.setSuccesses(deckUserWordPhraseTranslation.getSuccesses() + 1);
        }
    }
}
