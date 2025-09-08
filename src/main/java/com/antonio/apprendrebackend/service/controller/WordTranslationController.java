package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationWithPhraseTranslationsDTO;
import com.antonio.apprendrebackend.service.exception.WordTranslationNotFoundException;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.service.WordTranslationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/wordTranslation")
public class WordTranslationController {
    private static final Logger logger = LoggerFactory.getLogger(WordTranslationController.class);
    @Autowired
    private WordTranslationService wordTranslationService;


    /**
     * Get All WordTranslation and their Phrases associated given a deck
     *
     * @param deckId
     * @return HTTP respond with a List<WordTranslationWithPhrasesDTO>>
     * @throws WordTranslationNotFoundException if not exist any WordTranslation of the deck
     */
    @GetMapping(path = "{deckId}")
    public @ResponseBody ResponseEntity<List<WordTranslationWithPhraseTranslationsDTO>> getAllWordTranslationWithPhrasesByDeck(@PathVariable Integer deckId) {
        logger.info("Called getAllWordTranslationWithPhrasesByDeck() in WordTranslationController of deck-{}", deckId);

        SecurityContextHolder.getContext().getAuthentication().getCredentials();
        List<WordTranslationWithPhraseTranslationsDTO> phrasesWithWordTranslations = wordTranslationService.getAllWordTranslationsWithPhrasesByDeck(deckId);
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
        logger.info("Called getAllWordTranslationWithPhrasesByDeck() in WordTranslationController with pageNumber-{}, and pageSize-{}", pageNumber, pageSize);

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        List<WordTranslationDTO> words = wordTranslationService.getAllWordTranslations(pageNumber, pageSize, userInfo.getCurrentCourse().getTargetLanguage());
        return ResponseEntity.ok(words);
    }
}