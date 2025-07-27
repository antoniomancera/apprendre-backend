package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.AttemptResultDTO;
import com.antonio.apprendrebackend.service.dto.DeckDTO;
import com.antonio.apprendrebackend.service.dto.WordPhraseTranslationDTO;
import com.antonio.apprendrebackend.service.exception.DeckWordPhraseTranslationNotFoundException;
import com.antonio.apprendrebackend.service.mapper.DeckMapper;
import com.antonio.apprendrebackend.service.mapper.WordPhraseTranslationMapper;
import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.repository.DeckWordPhraseTranslationRespository;
import com.antonio.apprendrebackend.service.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DeckWordPhraseTranslationServiceImpl implements DeckWordPhraseTranslationService {
    private static final Logger logger = LoggerFactory.getLogger(DeckWordPhraseTranslationServiceImpl.class);

    @Autowired
    private DeckWordPhraseTranslationRespository deckWordPhraseTranslationRespository;

    @Autowired
    private DeckService deckService;

    @Autowired
    private WordPhraseTranslationService wordPhraseTranslationService;

    @Autowired
    private UserHistorialService userHistorialService;

    @Autowired
    private SuccessService successService;

    @Autowired
    private DeckMapper deckMapper;

    @Autowired
    private WordPhraseTranslationMapper wordPhraseTranslationMapper;

    /**
     * Return a DeckWordPhraseTranslation
     *
     * @param userId
     * @return DeckWordPhraseTranslation
     */
    @Override
    public DeckWordPhraseTranslation getRandomUserDeckWordPhraseTranslationWithByUser(Integer userId) {
        logger.debug(String.format("Get a random DeckWorPhraseTranslation for userId: %d", userId));

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
     * @return DeckWordPhraseTranslation
     */
    @Override
    public DeckWordPhraseTranslation getRandomUserDeckWordPhraseTranslationWithByDeckAndUser(Integer deckId) {
        logger.debug(String.format("Get a random DeckWorPhraseTranslation for deckId: %d", deckId));

        Optional<DeckWordPhraseTranslation> deckWordPhraseTranslation = deckWordPhraseTranslationRespository.findRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId);
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
     * @throws DeckWordPhraseTranslationNotFoundException
     */
    @Override
    public DeckWordPhraseTranslation getByDeckIdAndWordPhraseTranslationId(Integer deckId, Integer wordPhraseTranslationId) {
        logger.debug(String.format("Get the DeckWordPhraseTranslation of deckId: %d and wordPhraseTranslationId: %d", deckId, wordPhraseTranslationId));

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
        logger.debug(String.format("Get the list of wordTranslations of the deck: %d", deckId));

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
        logger.debug(String.format("Get the list of prhaseTranslations of the deck: %d", deckId));

        return deckWordPhraseTranslationRespository.findPhraseTranslationsByDeckId(deckId);
    }


    @Override
    public AttemptResultDTO attemptsWordPhraseTranslation(UserInfo userInfo, Integer wordPhraseId, Integer deckId, String attempt) {
        logger.debug(String.format("Attempt: %s of the wordPhrase: %d, for the deck: %d", attempt, wordPhraseId, deckId));

        DeckWordPhraseTranslation deckWordPhraseTranslation = getByDeckIdAndWordPhraseTranslationId(deckId, wordPhraseId);
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
     * Create a deck with a name and a description, besides, a list of deckWordPhraseTranslation is created linked
     * to the deck
     *
     * @param userInfo
     * @param name
     * @param description
     * @param wordPhraseTranslationIds
     * @return DeckDTO created
     * @throws WordPhraseTranslationNotFoundException if any wordPhraseTranslation is not found
     */
    @Override
    public DeckDTO createDeckWithWordPhraseTranslation(UserInfo userInfo, String name, String description, List<Integer> wordPhraseTranslationIds) {
        logger.debug(String.format("Create a deck: %s with a list of WordPhraseTranslation", name));

        Deck deck = deckService.createDeck(new Deck(userInfo, name, description));
        for (Integer id : wordPhraseTranslationIds) {
            deckWordPhraseTranslationRespository.save(new DeckWordPhraseTranslation(deck, wordPhraseTranslationService.getWordPhraseTranslationById(id)));
        }

        return deckMapper.toDTO(deck);
    }

    /**
     * Return a Random WordPhraseTranslationDTO depending on an optional deck
     *
     * @param deckId
     * @return WordPhraseTranslationDTO
     */
    @Override
    public WordPhraseTranslationDTO getRandomWordPhraseTranslation(UserInfo userInfo, Integer deckId) {
        logger.debug(String.format("Get a random WordPHraseTranslation of the deck: %d", deckId));

        DeckWordPhraseTranslation deckWordTranslation;
        if (deckId != null) {
            deckWordTranslation = getRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId);
        } else {
            deckWordTranslation = getRandomUserDeckWordPhraseTranslationWithByUser(userInfo.getId());
        }
        return wordPhraseTranslationMapper.toDTO(deckWordTranslation.getWordPhraseTranslation());
    }

    private static void updateStats(boolean success, DeckWordPhraseTranslation deckWordPhraseTranslation) {
        deckWordPhraseTranslation.setAttempts(deckWordPhraseTranslation.getAttempts() + 1);
        if (success) {
            deckWordPhraseTranslation.setSuccesses(deckWordPhraseTranslation.getSuccesses() + 1);
        }
    }
}
