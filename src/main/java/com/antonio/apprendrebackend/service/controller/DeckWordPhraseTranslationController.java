package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.dto.*;
import com.antonio.apprendrebackend.service.model.CreateDeckWithWordPhraseTranslationRequest;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.service.DeckWordPhraseTranslationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/deckWordPhrase")
public class DeckWordPhraseTranslationController {
    private static final Logger logger = LoggerFactory.getLogger(DeckWordPhraseTranslationController.class);

    @Autowired
    DeckWordPhraseTranslationService deckWordPhraseTranslationService;

    /**
     * Method that returns a word and a phrase, the deck is optional
     *
     * @param deckId
     * @param deckId
     * @return
     * @return HTTP respond with WordPhraseTranslationDTO
     * @throws DeckWordPhraseTranslationNotFoundException if not exist any wordPhraseTranslation for the user
     */
    @GetMapping(path = {"/getRandom/{deckId}", "/getRandom"})
    public @ResponseBody ResponseEntity<?> getRandomWordPhraseTranslation(@PathVariable(required = false) Integer deckId) {
        logger.info("Called getRandomWordPhraseTranslation() in DeckWordPhraseTranslationController for deck-{}", deckId);

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        WordPhraseTranslationDTO wordPhraseRandom = deckWordPhraseTranslationService.getRandomWordPhraseTranslation(userInfo, deckId);
        return ResponseEntity.ok(wordPhraseRandom);
    }

    /**
     * Attempt a WordPhraseTranslation, will add an userHistorial depending on the result of the attemps, besides returns a WordTranslation
     * in case of success
     *
     * @param wordPhraseId
     * @param deckId
     * @param attempt
     * @return HTTP respond with the AttemptResultDTO with result and new WordPhraseTranslation in case of success
     * @throws DeckWordPhraseTranslationNotFoundException if not exist anyone
     */
    @PutMapping(path = "/attempts/{wordPhraseId}")
    public @ResponseBody ResponseEntity<?> attemptsWordPhraseTranslation(@PathVariable Integer wordPhraseId, @RequestParam Integer deckId, @RequestParam String attempt) {
        logger.info("Called attemptsWordPhraseTranslation() in DeckWordPhraseTranslationController for deck-{}, attempt-{}, and wordPhrase", deckId, attempt, wordPhraseId);

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        AttemptResultDTO wordPhraseTranslationDTO = deckWordPhraseTranslationService.attemptsWordPhraseTranslation(userInfo, wordPhraseId, deckId, attempt);
        return ResponseEntity.ok(wordPhraseTranslationDTO);
    }

    /**
     * Create a deck with a name and a description, besides, a list of deckWordPhraseTranslation is created linked
     * to the deck
     *
     * @param request
     * @return HTTP respond with the DeckDTO created
     * @throws WordPhraseTranslationNotFoundException if any wordPhraseTranslation is not found
     */
    @PostMapping(path = "add")
    public @ResponseBody ResponseEntity<?> createDeckWithWordPhraseTranslation(@RequestBody CreateDeckWithWordPhraseTranslationRequest request) {
        logger.info("Called createDeckWithWordPhraseTranslation() in DeckWordPhraseTranslationController for requestName-{}, with description-{}, and wordPhraseTranslationIds", request.getName(), request.getDescription(), request.getWordPhraseTranslationIds());

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        DeckDTO deck = deckWordPhraseTranslationService.createDeckWithWordPhraseTranslation(userInfo, request.getName(), request.getDescription(), request.getWordPhraseTranslationIds());
        return ResponseEntity.ok(deck);
    }

    /**
     * Returns a page of WordWithAttemptsAndSuccess of a deck, that is a list of Words with their number of attempts
     * and accuracy in a deck
     *
     * @param deckId
     * @param pageNumber
     * @param pageSize
     * @return HTTP respond with a page of WordWithAttemptsAndSuccessDTO of a deck
     */
    @GetMapping(path = "wordsWithAttemptsAndSuccesses/paginated/{deckId}/{pageNumber}/{pageSize}")
    public @ResponseBody ResponseEntity<List<WordWithAttemptsAndSuccessDTO>> getWordsWithAttemptsAndSuccessPaginatedByDeckId(
            @PathVariable int deckId,
            @PathVariable int pageNumber,
            @PathVariable int pageSize
    ) {
        logger.info("Called getWordsWithAttemptsAndSuccessPaginatedByDeckId() in DeckWordPhraseTranslationController for deck-{}, pageNumber-{}, and pageSize-{}", deckId, pageNumber, pageSize);

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        List<WordWithAttemptsAndSuccessDTO> wordWithAttemptsAndSuccesses = deckWordPhraseTranslationService.getWordWithAttemptsAndSuccessPaginatedByDeckId(deckId, pageNumber, pageSize, userInfo.getId());
        return ResponseEntity.ok(wordWithAttemptsAndSuccesses);
    }

    /**
     * Returns the senses of a Word alongside all their categories number of attempts(in  general) and accuracy
     *
     * @param deckId
     * @param wordId
     * @return HTTP respond with a page of WordSenseInfoWithoutWordDTO of a deck
     */
    @GetMapping(path = "wordSenseInfo/{deckId}/{wordId}")
    public @ResponseBody ResponseEntity<List<WordSenseInfoWithoutWordDTO>> getWordSenseInfosWithoutWordByWordIdAndDeckId(
            @PathVariable int deckId,
            @PathVariable int wordId
    ) {
        logger.info("Called getWordSenseInfosWithoutWordByWordIdAndDeckId() in DeckWordPhraseTranslationController for deck-{}, and word-{}", deckId, wordId);

        SecurityContextHolder.getContext().getAuthentication().getCredentials();
        List<WordSenseInfoWithoutWordDTO> wordWithAttemptsAndSuccesses = deckWordPhraseTranslationService.getWordSenseInfosWithoutWordByWordIdAndDeckId(deckId, wordId);
        return ResponseEntity.ok(wordWithAttemptsAndSuccesses);
    }

    /**
     * Return a map with the words and wordSenses already in the deck, alongside the first page of a WordWithAttemptsAndSuccesses
     *
     * @param deckId
     * @param pageSize
     * @return HTTP respond with DeckEditInitInfoDTO
     */
    @GetMapping(path = "deckEditInit/{deckId}/{pageSize}")
    public @ResponseBody ResponseEntity<DeckEditInitInfoDTO> getDeckEditInit(
            @PathVariable int deckId,
            @PathVariable int pageSize
    ) {
        logger.info("Called getDeckEditInit() in DeckWordPhraseTranslationController for deck-{}, and pageSize-{}", deckId, pageSize);

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        DeckEditInitInfoDTO wordWithAttemptsAndSuccesses = deckWordPhraseTranslationService.getDeckEditInitInfo(deckId, pageSize, userInfo.getId());
        return ResponseEntity.ok(wordWithAttemptsAndSuccesses);
    }
}