package com.antonio.apprendrebackend.service.controller;

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
     * Method for will add false or true depending on the result of the attemps, returns a random wordTranslation
     *
     * @param wordId
     * @param phraseId
     * @param success
     * @param deckId
     * @return
     */
    @PutMapping(path = "/attempts/{wordId}")
    public @ResponseBody ResponseEntity<?> attemptsWordPhraseTranslation(@PathVariable Integer wordId, @RequestParam Integer phraseId, @RequestParam boolean success, @RequestParam Integer deckId) {
        //WordTranslationDTO wordTranslationDTO = wordPhraseTranslationService.attemptsWordTranslation(wordId, phraseId, success, deckId);
        //return ResponseEntity.ok(wordTranslationDTO);
        return null;
    }
}