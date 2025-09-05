package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.dto.WordPhraseTranslationDTO;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.service.WordPhraseTranslationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public @ResponseBody ResponseEntity<List<WordPhraseTranslationDTO>> getAllCurrentCourseWordPhraseTranslationByWordSenseIds(@RequestBody List<Integer> senseIds) {
        logger.info("Called getAllCurrentCourseWordPhraseTranslationByWordSenseIds in WordPhraseTranslationController");

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        List<WordPhraseTranslationDTO> wordPhraseTranslations = wordPhraseTranslationService.getAllCurrentCourseWordPhraseTranslationByWordSenseIds(senseIds, userInfo.getCurrentCourse());
        return ResponseEntity.ok(wordPhraseTranslations);
    }

}
