package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.PhraseTranslationDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationWithPhraseTranslationsDTO;
import com.antonio.apprendrebackend.service.exception.WordTranslationNotFoundException;
import com.antonio.apprendrebackend.service.mapper.PhraseTranslationMapper;
import com.antonio.apprendrebackend.service.mapper.WordTranslationMapper;
import com.antonio.apprendrebackend.service.model.Deck;
import com.antonio.apprendrebackend.service.model.Language;
import com.antonio.apprendrebackend.service.model.PhraseTranslation;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import com.antonio.apprendrebackend.service.repository.WordTranslationRepository;
import com.antonio.apprendrebackend.service.service.DeckService;
import com.antonio.apprendrebackend.service.service.DeckWordPhraseTranslationService;
import com.antonio.apprendrebackend.service.service.WordPhraseTranslationService;
import com.antonio.apprendrebackend.service.service.WordTranslationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class WordTranslationServiceImpl implements WordTranslationService {
    private static final Logger logger = LoggerFactory.getLogger(WordTranslationServiceImpl.class);
    @Autowired
    private WordTranslationRepository wordTranslationRepository;
    @Autowired
    private WordPhraseTranslationService wordPhraseTranslationService;
    @Autowired
    private WordTranslationMapper wordTranslationMapper;
    @Autowired
    private DeckService deckService;
    @Autowired
    private PhraseTranslationMapper phraseTranslationMapper;
    @Autowired
    private DeckWordPhraseTranslationService deckWordPhraseTranslationService;

    /**
     * Get All WordTranslation and their Phrases associated given a deck
     *
     * @param deckId
     * @return List<WordTranslationWithPhrasesDTO> of a deck
     * @throws WordTranslationNotFoundException if not exist any WordTranslation of the deck
     */
    @Override
    public List<WordTranslationWithPhraseTranslationsDTO> getAllWordTranslationsWithPhrasesByDeck(Integer deckId) {
        logger.debug("Called getAllWordTranslationsWithPhrasesByDeck() in WordTranslationService for deck-{}", deckId);

        List<WordTranslation> wordTranslations = deckWordPhraseTranslationService.getWordTranslationsByDeckId(deckId);
        if (wordTranslations.isEmpty() || wordTranslations.size() == 0) {
            throw new WordTranslationNotFoundException("Not found any wordTranslation");
        }

        Deck deck = deckService.getDeckbyId(deckId);

        return wordTranslations.stream().map(word -> {
            List<PhraseTranslation> phrases = wordPhraseTranslationService.getPhrasesByDeckIdAndWordTranslationId(deckId, word.getId());
            List<PhraseTranslationDTO> phrasesDTO = phrases.stream()
                    .map(phraseTranslation -> phraseTranslationMapper.toDTO(phraseTranslation, deck.getCourse().getTargetLanguage()))
                    .collect(Collectors.toList());

            return new WordTranslationWithPhraseTranslationsDTO(
                    wordTranslationMapper.toDTO(word, deck.getCourse().getTargetLanguage()),
                    phrasesDTO
            );
        }).collect(Collectors.toList());


    }

    /**
     * Get the page pageNumber of WordTranslationDTO with pageSize elements of a language
     *
     * @param pageNumber
     * @param pageSize
     * @param language
     * @return List<WordTranslationDTO>
     * @throws WordTranslationNotFoundException if not exist any WordTranslation
     */
    @Override
    public List<WordTranslationDTO> getAllWordTranslations(Integer pageNumber, Integer pageSize, Language language) {
        logger.debug("Called getAllWordTranslations() in WordTranslationService for pageNumber-{}, pageSize-{}, and language-{}", pageNumber, pageSize, language);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<WordTranslationDTO> words = wordTranslationRepository
                .findAll(pageable)
                .stream()
                .map(wordTranslation -> wordTranslationMapper.toDTO(wordTranslation, language))
                .collect(Collectors.toList());

        if (words.isEmpty()) {
            throw new WordTranslationNotFoundException("Not found any phrase");
        }
        return words;
    }
}