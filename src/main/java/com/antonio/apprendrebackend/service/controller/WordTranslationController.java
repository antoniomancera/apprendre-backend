package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.exception.ErrorCode;
import com.antonio.apprendrebackend.service.model.DailyStats;
import com.antonio.apprendrebackend.service.model.ErrorResponse;
import com.antonio.apprendrebackend.service.service.WordTranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/wordTranslation")
public class WordTranslationController {
    @Autowired
    WordTranslationService wordTranslationService;

    /**
     * Method that returns a word and a phrase, the deck is optional
     *
     * @param deckId
     * @return
     */
    @GetMapping(path = {"/getRandom/{deckId}", "/getRandom"})
    public @ResponseBody ResponseEntity<?> getRandomWordTranslationPhrase(@PathVariable(required = false) Integer deckId) {
        WordTranslationDTO wordTranslationDTO = wordTranslationService.getRandomWordTranslation(deckId);
        return ResponseEntity.ok(wordTranslationDTO);
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
    public @ResponseBody ResponseEntity<?> attemptsWordTranslation(@PathVariable Integer wordId, @RequestParam Integer phraseId, @RequestParam boolean success, @RequestParam(required = false) Integer deckId) {
        WordTranslationDTO wordTranslationDTO = wordTranslationService.attemptsWordTranslation(wordId, phraseId, success, deckId);
        return ResponseEntity.ok(wordTranslationDTO);
    }
}