package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.dto.PhraseWithWordTranslationsDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
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

    @GetMapping(path = "{deckId}")
    public @ResponseBody ResponseEntity<?> getAllPhrasesWithWordTranslationsByDeck(@PathVariable Integer deckId) {
        List<PhraseWithWordTranslationsDTO> phrasesWithWordTranslations = phraseService.getAllPhrasesWithWordTranslationsByDeck(deckId);
        return ResponseEntity.ok(phrasesWithWordTranslations);
    }
}
