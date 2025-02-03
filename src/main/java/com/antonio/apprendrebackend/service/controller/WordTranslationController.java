package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationWithPhrasesDTO;
import com.antonio.apprendrebackend.service.exception.PhraseNotFoundException;
import com.antonio.apprendrebackend.service.exception.WordTranslationNotFoundException;
import com.antonio.apprendrebackend.service.service.WordTranslationService;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * Get All WordTranslation and their Phrases associated given a deck
     *
     * @param deckId
     * @return HTTP respond with a List<WordTranslationWithPhrasesDTO>>
     * @throws PhraseNotFoundException if not exist any WordTranslation of the deck
     */
    @GetMapping(path = "{deckId}")
    public @ResponseBody ResponseEntity<List<WordTranslationWithPhrasesDTO>> getAllWordTranslationWithPhrasesByDeck(@PathVariable Integer deckId) {
        List<WordTranslationWithPhrasesDTO> phrasesWithWordTranslations = wordTranslationService.getAllWordTranslationsWithPhrasesByDeck(deckId);
        return ResponseEntity.ok(phrasesWithWordTranslations);
    }

    /**
     * Get the page pageNumber of WordTranslationDTO with pageSize elements
     *
     * @param pageNumber
     * @param pageSize
     * @return HTTP respond with a List<WordTranslationDTO>
     * @throws WordTranslationNotFoundException if not exist any WordTranslation
     */
    @GetMapping(path = "/paginated/{pageNumber}/{pageSize}")
    public @ResponseBody ResponseEntity<List<WordTranslationDTO>> getAllWordTranslations(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        List<WordTranslationDTO> words = wordTranslationService.getAllWordTranslations(pageNumber, pageSize);
        return ResponseEntity.ok(words);
    }
}