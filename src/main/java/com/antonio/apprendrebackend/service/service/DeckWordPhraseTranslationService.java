package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.*;
import com.antonio.apprendrebackend.service.exception.DeckWordPhraseTranslationNotFoundException;
import com.antonio.apprendrebackend.service.model.*;

import java.util.List;

public interface DeckWordPhraseTranslationService {
    /**
     * Return a random DeckWordPhraseTranslation of an user
     *
     * @param userId
     * @return DeckWordPhraseTranslation
     */
    DeckWordPhraseTranslation getRandomUserDeckWordPhraseTranslationWithByUser(Integer userId);


    /**
     * Return a random DeckWordPhraseTranslation from a deck
     *
     * @param deckId
     * @return DeckWordPhraseTranslation
     */
    DeckWordPhraseTranslation getRandomUserDeckWordPhraseTranslationWithByDeckAndUser(Integer deckId);

    /**
     * Get DeckWordPhraseTranslation given a Deck and a WordPhraseTranslation
     *
     * @param deckId
     * @param wordPhraseTranslationId
     * @return a DeckWordPhraseTranslation
     * @throws DeckWordPhraseTranslationNotFoundException
     */
    DeckWordPhraseTranslation getByDeckIdAndWordPhraseTranslationId(Integer deckId, Integer wordPhraseTranslationId);

    /**
     * Return all WordTranslation of a deck
     *
     * @param deckId
     * @return List<WordTranslation>
     */
    List<WordTranslation> getWordTranslationsByDeckId(Integer deckId);

    /**
     * Return all PhraseTranslation of a deck
     *
     * @param deckId
     * @return List<PhraseTranslation>
     */
    List<PhraseTranslation> getPhraseTranslationsByDeckId(Integer deckId);

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
     * Return a Random WordPhraseTranslationDTO depending on an optional deck
     *
     * @param deckId
     * @return WordPhraseTranslationDTO
     */
    WordPhraseTranslationDTO getRandomWordPhraseTranslation(UserInfo userInfo, Integer deckId);

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
    DeckDTO createDeckWithWordPhraseTranslation(UserInfo userInfo, String name, String description, List<Integer> wordPhraseTranslationIds);

    /**
     * Returns a page of WordWithAttemptsAndSuccess of a deck, that is a list of Words with their number of ssucces
     * and accuracy in a deck
     *
     * @param deckId
     * @param pageNumber
     * @param pageSize
     * @return List<WordWithAttemptsAndSuccessDTO>
     */
    List<WordWithAttemptsAndSuccessDTO> getWordWithAttemptsAndSuccessPaginatedByDeckId(Integer deckId, Integer pageNumber, Integer pageSize, Integer userId);

    /**
     * Returns the senses of a Word alongside all their categories number of attempts(in  general) and accuracy
     *
     * @param deckId
     * @param wordId
     * @return List<WordSenseInfoWithoutWordDTO>
     */
    List<WordSenseInfoWithoutWordDTO> getWordSenseInfosWithoutWordByWordIdAndDeckId(Integer deckId, Integer wordId);

    /**
     * Return a map with the words and wordSenses already in the deck, alongside the first page of a WordWithAttemptsAndSuccesses
     *
     * @param deckId
     * @param pageSize
     * @return DeckEditInitInfoDTO
     */
    DeckEditInitInfoDTO getDeckEditInitInfo(Integer deckId, Integer pageSize, Integer userId);
}


