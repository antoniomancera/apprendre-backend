package com.antonio.apprendrebackend.controller;

import com.antonio.apprendrebackend.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.model.ErrorResponse;
import com.antonio.apprendrebackend.service.WordTranslationService;
import com.antonio.apprendrebackend.util.ErrorCode;
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


    @GetMapping(path = "/getRandom")
    public @ResponseBody ResponseEntity<?> getRandomWordTranslationPhrase() {
        WordTranslationDTO wordTranslationDTO = wordTranslationService.getRandomWordTranslation();
        if (wordTranslationDTO == null) {
            ErrorResponse errorResponse = new ErrorResponse("No se ha encontrado WordTranslation", ErrorCode.WORD_TRANSLATION_NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(wordTranslationDTO, HttpStatus.CREATED);
    }

    @PutMapping(path = "/attempts/{wordId}")
    public @ResponseBody ResponseEntity<?> attemptsWordTranslation(@PathVariable int wordId, @RequestParam int phraseId, @RequestParam boolean success) {
        WordTranslationDTO wordTranslationDTO = wordTranslationService.attemptsWordTranslation(wordId, phraseId, success);
        if (wordTranslationDTO == null) {
            ErrorResponse errorResponse = new ErrorResponse("No se ha encontrado WordTranslation o phrase", ErrorCode.PHRASE_NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(wordTranslationDTO, HttpStatus.CREATED);
    }

    
}