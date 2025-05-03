package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.AttemptResultDTO;
import com.antonio.apprendrebackend.service.dto.WordPhraseTranslationDTO;
import com.antonio.apprendrebackend.service.mapper.WordPhraseTranslationMapper;
import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.repository.WordPhraseTranslationRepository;
import com.antonio.apprendrebackend.service.service.DeckWordPhraseTranslationService;
import com.antonio.apprendrebackend.service.service.UserHistorialService;
import com.antonio.apprendrebackend.service.service.WordPhraseTranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordPhraseTranslationServiceServiceImpl implements WordPhraseTranslationService {

    @Autowired
    DeckWordPhraseTranslationService deckWordPhraseTranslationService;

    @Autowired
    WordPhraseTranslationMapper wordPhraseTranslationMapper;

    @Autowired
    UserHistorialService userHistorialService;

    @Autowired
    WordPhraseTranslationRepository wordPhraseTranslationRepository;

    /**
     * Return a Random WordTranslation depending on an optional deck
     *
     * @param deckId
     * @return WordPhraseTranslationDTO
     */
    @Override
    public WordPhraseTranslationDTO getRandomWordPhraseTranslation(UserInfo userInfo, Integer deckId) {
        DeckWordPhraseTranslation deckWordTranslation;
        if (deckId != null) {
            deckWordTranslation = deckWordPhraseTranslationService.getRandomUserDeckWordPhraseTranslationWithByDeckAndUser(userInfo.getId(), deckId);
        } else {
            deckWordTranslation = deckWordPhraseTranslationService.getRandomUserDeckWordPhraseTranslationWithByUser(userInfo.getId());
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
     * @throws DeckWordPhraseTranslationNotFoundException if not exist anyone
     */
    @Override
    public AttemptResultDTO attemptsWordPhraseTranslation(UserInfo userInfo, Integer wordPhraseId, Integer deckId, String attempt) {
        DeckWordPhraseTranslation deckWordPhraseTranslation = deckWordPhraseTranslationService.getByDeckIdAndWordPhraseTranslationId(deckId, wordPhraseId);
        Boolean hasSuccess = deckWordPhraseTranslation.getWordPhraseTranslation().getWordTranslation().getWordSenseFr().getWord().getName().equals(attempt);
        updateStats(hasSuccess, deckWordPhraseTranslation);
        userHistorialService.postUserHistorial(new UserHistorial(deckWordPhraseTranslation, hasSuccess ? 1 : 0, deckId));
        if (hasSuccess) {
            return new AttemptResultDTO(true, getRandomWordPhraseTranslation(userInfo, deckId));
        }
        return new AttemptResultDTO(false, null);
    }

    /**
     * Get all PhraseTranslation associated to a WordTranslation of a Deck
     *
     * @param deckId
     * @param wordTranslationId
     * @return List<Phrase>
     */
    @Override
    public List<PhraseTranslation> getPhrasesByDeckIdAndWordTranslationId(Integer deckId, Integer wordTranslationId) {
        return wordPhraseTranslationRepository.findPhrasesByDeckIdAndWordTranslationId(deckId, wordTranslationId);
    }

    /**
     * Get all WordTranslation associated to a phraseTranslation of a Deck
     *
     * @param deckId
     * @param phraseTranslationId
     * @return List<WordTranslation>
     */
    @Override
    public List<WordTranslation> getWordTranslationsByDeckIdPhraseTranslationId(Integer deckId, Integer phraseTranslationId) {
        return wordPhraseTranslationRepository.findWordTranslationsByDeckIdPhraseTranslationId(deckId, phraseTranslationId);
    }


    private static void updateStats(boolean success, DeckWordPhraseTranslation deckWordPhraseTranslation) {
        deckWordPhraseTranslation.setAttempts(deckWordPhraseTranslation.getAttempts() + 1);
        if (success) {
            deckWordPhraseTranslation.setSuccesses(deckWordPhraseTranslation.getSuccesses() + 1);
        }
    }
}
