package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.dto.PhraseDTO;
import com.antonio.apprendrebackend.service.dto.PhraseWithWordTranslationsDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.exception.PhraseNotFoundException;
import com.antonio.apprendrebackend.service.model.Phrase;
import com.antonio.apprendrebackend.service.service.PhraseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/phrase")
public class PhraseController {
    @Autowired
    PhraseService phraseService;

    /**
     * Get All Phrases and their WordTranslation of a deck
     *
     * @param deckId
     * @return HTTP respond with a List<PhraseWithWordTranslationsDTO>>
     * @throws PhraseNotFoundException if not exist any Phrase of the deck
     */
    @GetMapping(path = "{deckId}")
    public @ResponseBody ResponseEntity<List<PhraseWithWordTranslationsDTO>> getAllPhrasesWithWordTranslationsByDeck(@PathVariable Integer deckId) {
        List<PhraseWithWordTranslationsDTO> phrasesWithWordTranslations = phraseService.getAllPhrasesWithWordTranslationsByDeck(deckId);
        return ResponseEntity.ok(phrasesWithWordTranslations);
    }

    /**
     * Get the page pageNumber of PhraseDTO with pageSize elements
     *
     * @param pageNumber
     * @param pageSize
     * @return List<PhraseDTO>
     * @throws PhraseNotFoundException if not exist any Phrase
     */
    @GetMapping(path = "/paginated/{pageNumber}/{pageSize}")
    public @ResponseBody ResponseEntity<List<PhraseDTO>> getAllPhrases(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        List<PhraseDTO> phrases = phraseService.getAllPhrases(pageNumber, pageSize);
        return ResponseEntity.ok(phrases);
    }
}
