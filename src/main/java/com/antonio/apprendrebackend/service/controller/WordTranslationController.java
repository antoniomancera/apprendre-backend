package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.model.ErrorResponse;
import com.antonio.apprendrebackend.service.service.WordTranslationService;
import com.antonio.apprendrebackend.service.util.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(path = "/getRandom/{deckId}")
    public @ResponseBody ResponseEntity<?> getRandomWordTranslationPhrase(@PathVariable(required = false) Integer deckId) {
        WordTranslationDTO wordTranslationDTO = wordTranslationService.getRandomWordTranslation(deckId);
        if (wordTranslationDTO == null) {
            ErrorResponse errorResponse = new ErrorResponse("No se ha encontrado WordTranslation", ErrorCode.WORD_TRANSLATION_NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(wordTranslationDTO, HttpStatus.CREATED);
    }

    /**
     * Method for will add false or true depending of th result of the attemps, returns  a word and a phrase
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
        if (wordTranslationDTO == null) {
            ErrorResponse errorResponse = new ErrorResponse("No se ha encontrado WordTranslation o phrase", ErrorCode.PHRASE_NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(wordTranslationDTO, HttpStatus.CREATED);
    }


}