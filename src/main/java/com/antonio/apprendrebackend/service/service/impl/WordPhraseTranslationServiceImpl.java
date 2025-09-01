package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.AttemptResultDTO;
import com.antonio.apprendrebackend.service.dto.WordPhraseTranslationDTO;
import com.antonio.apprendrebackend.service.exception.DeckWordPhraseTranslationNotFoundException;
import com.antonio.apprendrebackend.service.exception.WordPhraseTranslationNotFoundException;
import com.antonio.apprendrebackend.service.exception.WordSenseNotFoundException;
import com.antonio.apprendrebackend.service.mapper.WordPhraseTranslationMapper;
import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.repository.WordPhraseTranslationRepository;
import com.antonio.apprendrebackend.service.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordPhraseTranslationServiceImpl implements WordPhraseTranslationService {
    private static final Logger logger = LoggerFactory.getLogger(WordPhraseTranslationServiceImpl.class);

    @Autowired
    private WordPhraseTranslationMapper wordPhraseTranslationMapper;

    @Autowired
    private WordPhraseTranslationRepository wordPhraseTranslationRepository;

    /**
     * Get all PhraseTranslation associated to a WordTranslation of a Deck
     *
     * @param deckId
     * @param wordTranslationId
     * @return List<Phrase>
     */
    @Override
    public List<PhraseTranslation> getPhrasesByDeckIdAndWordTranslationId(Integer deckId, Integer wordTranslationId) {
        logger.debug(String.format("Getting all the phrases for deck: %d, and wordTranslation: %d", deckId, wordTranslationId));

        return wordPhraseTranslationRepository.findPhrasesByDeckIdAndWordTranslationId(deckId, wordTranslationId);
    }

    /**
     * Get all WordTranslation associated to a phraseTranslation of a Deck
     *
     * @param deckId
     * @param phraseTranslationId
     * @return List<WordTranslation>
     */
    @Override
    public List<WordTranslation> getWordTranslationsByDeckIdPhraseTranslationId(Integer deckId, Integer phraseTranslationId) {
        logger.debug(String.format("Getting all the wordTranslations for deck: %d, and phrase: %d", deckId, phraseTranslationId));

        return wordPhraseTranslationRepository.findWordTranslationsByDeckIdPhraseTranslationId(deckId, phraseTranslationId);
    }

    /**
     * Get all WordTranslation associated to a phraseTranslation of a Deck
     *
     * @param senseIds
     * @return List<WordTranslation>
     */
    @Override
    public List<WordPhraseTranslationDTO> getAllWordPhraseTranslationByWordSense(List<Integer> senseIds) {
        logger.debug("Get all the WOrdPhraseTranslation given a list of wordSenses");

        return wordPhraseTranslationRepository.findByWordSenseIds(senseIds)
                .stream()
                .map(wordPhraseTranslationMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get a WordPhraseTranslation given their id
     *
     * @param wordPhraseTranslationId
     * @return WordPhraseTranslation
     * @throws WordPhraseTranslationNotFoundException
     */
    @Override
    public WordPhraseTranslation getWordPhraseTranslationById(Integer wordPhraseTranslationId) {
        logger.debug("Get a WordPhraseTranslation given with id: %d", wordPhraseTranslationId);
        
        return wordPhraseTranslationRepository.findById(wordPhraseTranslationId).orElseThrow(() -> new WordPhraseTranslationNotFoundException(String.format("Not found any wordPhraseTranslation with id: %s", wordPhraseTranslationId)));
    }

}
