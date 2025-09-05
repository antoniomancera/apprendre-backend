package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.dto.PhraseTranslationDTO;
import com.antonio.apprendrebackend.service.dto.PhraseTranslationWithWordTranslationsDTO;
import com.antonio.apprendrebackend.service.exception.PhraseNotFoundException;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.service.PhraseTranslationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/phrase")
public class PhraseTranslationController {
    private static final Logger logger = LoggerFactory.getLogger(PhraseTranslationController.class);

    @Autowired
    private PhraseTranslationService phraseTranslationService;

    /**
     * Get All Phrases and their WordTranslation of a deck
     *
     * @param deckId
     * @return HTTP respond with a List<PhraseWithWordTranslationsDTO>>
     * @throws PhraseNotFoundException if not exist any Phrase of the deck
     */
    @GetMapping(path = "{deckId}")
    public @ResponseBody ResponseEntity<List<PhraseTranslationWithWordTranslationsDTO>> getAllPhrasesWithWordTranslationsByDeck(@PathVariable Integer deckId) {
        logger.info("Called getAllPhrasesWithWordTranslationsByDeck() in PhraseTranslationController for deck-{}", deckId);

        SecurityContextHolder.getContext().getAuthentication().getCredentials();
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
    public @ResponseBody ResponseEntity<List<PhraseTranslationDTO>> getAllTargetLanguagePhrases(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        logger.info("Called getAllTargetLanguagePhrases() in PhraseTranslationController for pageNumber-{},and pageSize-{}", pageNumber, pageSize);

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        List<PhraseTranslationDTO> phrases = phraseTranslationService.getAllTargetLanguagePhrases(pageNumber, pageSize, userInfo.getCurrentCourse().getTargetLanguage());
        return ResponseEntity.ok(phrases);
    }
}
