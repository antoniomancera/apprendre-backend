package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.dto.AttemptResultDTO;
import com.antonio.apprendrebackend.service.dto.WordPhraseTranslationDTO;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.service.WordPhraseTranslationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/deckWordPhrase")
public class DeckWordPhraseTranslationController {
    private static final Logger logger = LoggerFactory.getLogger(DeckWordPhraseTranslationController.class);

    @Autowired
    WordPhraseTranslationService wordPhraseTranslationService;

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
        logger.info(String.format("Get a random WordPhraseTranslation of the deck: %d", deckId));

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        WordPhraseTranslationDTO wordPhraseRandom = wordPhraseTranslationService.getRandomWordPhraseTranslation(userInfo, deckId);
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
        logger.info(String.format("Attempts: %s, for the wordPhraseTranslation:  %d, of deck: %d", attempt, wordPhraseId, deckId));

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        AttemptResultDTO wordPhraseTranslationDTO = wordPhraseTranslationService.attemptsWordPhraseTranslation(userInfo, wordPhraseId, deckId, attempt);
        return ResponseEntity.ok(wordPhraseTranslationDTO);
    }
}