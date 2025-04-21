package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.dto.PhraseTranslationDTO;
import com.antonio.apprendrebackend.service.dto.PhraseTranslationWithWordTranslationsDTO;
import com.antonio.apprendrebackend.service.exception.PhraseNotFoundException;
import com.antonio.apprendrebackend.service.service.PhraseTranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/phrase")
public class PhraseTranslationController {
    @Autowired
    PhraseTranslationService phraseTranslationService;

    /**
     * Get All Phrases and their WordTranslation of a deck
     *
     * @param deckId
     * @return HTTP respond with a List<PhraseWithWordTranslationsDTO>>
     * @throws PhraseNotFoundException if not exist any Phrase of the deck
     */
    @GetMapping(path = "{deckId}")
    public @ResponseBody ResponseEntity<List<PhraseTranslationWithWordTranslationsDTO>> getAllPhrasesWithWordTranslationsByDeck(@PathVariable Integer deckId) {
        List<PhraseTranslationWithWordTranslationsDTO> phrasesWithWordTranslations = phraseTranslationService.getAllPhrasesWithWordTranslationsByDeck(deckId);
        return ResponseEntity.ok(phrasesWithWordTranslations);
    }

    /**
     * Get the page pageNumber of PhraseTranslationDTO with pageSize elements
     *
     * @param pageNumber
     * @param pageSize
     * @return List<PhraseDTO>
     * @throws PhraseNotFoundException if not exist any Phrase
     */
    @GetMapping(path = "/paginated/{pageNumber}/{pageSize}")
    public @ResponseBody ResponseEntity<List<PhraseTranslationDTO>> getAllPhrases(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        List<PhraseTranslationDTO> phrases = phraseTranslationService.getAllPhrases(pageNumber, pageSize);
        return ResponseEntity.ok(phrases);
    }
}
