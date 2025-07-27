package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.dto.WordPhraseTranslationDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationWithPhraseTranslationsDTO;
import com.antonio.apprendrebackend.service.exception.WordTranslationNotFoundException;
import com.antonio.apprendrebackend.service.service.WordPhraseTranslationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/wordPhraseTranslation")
public class WordPhraseTranslationController {
    private static final Logger logger = LoggerFactory.getLogger(WordPhraseTranslationController.class);
    @Autowired
    private WordPhraseTranslationService wordPhraseTranslationService;


    /**
     * Get all WordTranslation associated to a phraseTranslation of a Deck
     *
     * @param deckId
     * @param phraseTranslationId
     * @return HTTP respond with a List<WordTranslation>
     */
    @PostMapping(path = "allByWordSenses")
    public @ResponseBody ResponseEntity<List<WordPhraseTranslationDTO>> getAllWordPhraseTranslationByWordSenses(@RequestBody List<Integer> senseIds) {
        logger.info("Get all the WOrdPhraseTranslation given a list of wordSenses");

        List<WordPhraseTranslationDTO> wordPhraseTranslations = wordPhraseTranslationService.getAllWordPhraseTranslationByWordSense(senseIds);
        return ResponseEntity.ok(wordPhraseTranslations);
    }

}
