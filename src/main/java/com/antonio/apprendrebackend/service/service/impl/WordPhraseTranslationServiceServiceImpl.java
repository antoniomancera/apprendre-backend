package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.AttemptResultDTO;
import com.antonio.apprendrebackend.service.dto.WordPhraseTranslationDTO;
import com.antonio.apprendrebackend.service.mapper.WordPhraseTranslationMapper;
import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.repository.WordPhraseTranslationRepository;
import com.antonio.apprendrebackend.service.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordPhraseTranslationServiceServiceImpl implements WordPhraseTranslationService {
    private static final Logger logger = LoggerFactory.getLogger(WordPhraseTranslationServiceServiceImpl.class);

    @Autowired
    private DeckWordPhraseTranslationService deckWordPhraseTranslationService;

    @Autowired
    private WordPhraseTranslationMapper wordPhraseTranslationMapper;

    @Autowired
    private UserHistorialService userHistorialService;

    @Autowired
    private DeckService deckService;

    @Autowired
    private SuccessService successService;

    @Autowired
    private WordPhraseTranslationRepository wordPhraseTranslationRepository;

    /**
     * Return a Random WordTranslation depending on an optional deck
     *
     * @param deckId
     * @return WordPhraseTranslationDTO
     */
    @Override
    public WordPhraseTranslationDTO getRandomWordPhraseTranslation(UserInfo userInfo, Integer deckId) {
        logger.info(String.format("Get a random WordPhraseTranslation of the deck: %d", deckId));

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
        logger.info(String.format("Attempts: %s, for the wordPhraseTranslation:  %d, of deck: %d", attempt, wordPhraseId, deckId));

        DeckWordPhraseTranslation deckWordPhraseTranslation = deckWordPhraseTranslationService.getByDeckIdAndWordPhraseTranslationId(deckId, wordPhraseId);
        Deck deck = deckService.getDeckbyId(deckId);
        Boolean hasSuccess = deckWordPhraseTranslation.getWordPhraseTranslation().getWordTranslation().getWordSenseFr().getWord().getName().equals(attempt);
        updateStats(hasSuccess, deckWordPhraseTranslation);
        userHistorialService.postUserHistorial(new UserHistorial(deckWordPhraseTranslation, userInfo, successService.getSuccessByAttemptAndWordSense(attempt, deckWordPhraseTranslation.getWordPhraseTranslation().getWordTranslation().getWordSenseFr()), deck));
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
        logger.info(String.format("Getting all the phrases for deck: %d, and wordTranslation: %d", deckId, wordTranslationId));

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
        logger.info(String.format("Getting all the wordTranslations for deck: %d, and phrase: %d", deckId, phraseTranslationId));

        return wordPhraseTranslationRepository.findWordTranslationsByDeckIdPhraseTranslationId(deckId, phraseTranslationId);
    }


    private static void updateStats(boolean success, DeckWordPhraseTranslation deckWordPhraseTranslation) {
        deckWordPhraseTranslation.setAttempts(deckWordPhraseTranslation.getAttempts() + 1);
        if (success) {
            deckWordPhraseTranslation.setSuccesses(deckWordPhraseTranslation.getSuccesses() + 1);
        }
    }
}
