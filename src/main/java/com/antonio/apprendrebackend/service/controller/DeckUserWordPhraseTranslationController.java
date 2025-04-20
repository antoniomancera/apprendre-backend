package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.dto.AttemptResultDTO;
import com.antonio.apprendrebackend.service.dto.DeckUserWordPhraseTranslationDTO;
import com.antonio.apprendrebackend.service.dto.WordPhraseTranslationDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.exception.PhraseNotFoundException;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.service.DeckUserWordPhraseTranslationService;
import com.antonio.apprendrebackend.service.service.WordPhraseTranslationService;
import com.antonio.apprendrebackend.service.service.WordTranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/deckUserWordPhrase")
public class DeckUserWordPhraseTranslationController {
    @Autowired
    WordPhraseTranslationService wordPhraseTranslationService;

    /**
     * Method that returns a word and a phrase, the deck is optional
     *
     * @param deckId
     * @param deckId
     * @return
     * @return HTTP respond with WordPhraseTranslationDTO
     * @throws DeckUserWordPhraseTranslationNotFoundException if not exist any wordPhraseTranslation for the user
     */
    @GetMapping(path = {"/getRandom/{deckId}", "/getRandom"})
    public @ResponseBody ResponseEntity<?> getRandomWordPhraseTranslation(@PathVariable(required = false) Integer deckId) {
        UserInfo userInfo = new UserInfo(1, "1");
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
     * @throws DeckUserWordPhraseTranslationNotFoundException if not exist anyone
     */
    @PutMapping(path = "/attempts/{wordPhraseId}")
    public @ResponseBody ResponseEntity<?> attemptsWordPhraseTranslation(@PathVariable Integer wordPhraseId, @RequestParam Integer deckId, @RequestParam String attempt) {
        UserInfo userInfo = new UserInfo(1, "1");
        AttemptResultDTO wordPhraseTranslationDTO = wordPhraseTranslationService.attemptsWordPhraseTranslation(userInfo, wordPhraseId, deckId, attempt);
        return ResponseEntity.ok(wordPhraseTranslationDTO);
    }
}