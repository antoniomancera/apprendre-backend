package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.PhraseTranslationDTO;
import com.antonio.apprendrebackend.service.dto.PhraseTranslationWithWordTranslationsDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.exception.PhraseNotFoundException;
import com.antonio.apprendrebackend.service.mapper.PhraseTranslationMapper;
import com.antonio.apprendrebackend.service.mapper.WordTranslationMapper;
import com.antonio.apprendrebackend.service.model.Deck;
import com.antonio.apprendrebackend.service.model.Language;
import com.antonio.apprendrebackend.service.model.PhraseTranslation;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import com.antonio.apprendrebackend.service.repository.PhraseTranslationRepository;
import com.antonio.apprendrebackend.service.service.DeckService;
import com.antonio.apprendrebackend.service.service.DeckWordPhraseTranslationService;
import com.antonio.apprendrebackend.service.service.PhraseTranslationService;
import com.antonio.apprendrebackend.service.service.WordPhraseTranslationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhraseTranslationServiceImpl implements PhraseTranslationService {
    private static final Logger logger = LoggerFactory.getLogger(PhraseTranslationServiceImpl.class);

    @Autowired
    private PhraseTranslationRepository phraseTranslationRepository;
    @Autowired
    private WordPhraseTranslationService wordPhraseTranslationService;
    @Autowired
    private DeckWordPhraseTranslationService deckWordPhraseTranslationService;
    @Autowired
    private DeckService deckService;
    @Autowired
    private WordTranslationMapper wordTranslationMapper;
    @Autowired
    private PhraseTranslationMapper phraseTranslationMapper;

    /**
     * Get All Phrases and their WordTranslation of a deck
     *
     * @param deckId
     * @return List<PhraseTranslationWithWordTranslationsDTO> of a deck
     * @throws PhraseNotFoundException if not exist any Phrase of the deck
     */
    @Override
    public List<PhraseTranslationWithWordTranslationsDTO> getAllPhrasesWithWordTranslationsByDeck(Integer deckId) {
        logger.debug("Called getAllPhrasesWithWordTranslationsByDeck in PhraseTranslationServiceImpl for deck-{}", deckId);

        List<PhraseTranslation> phrases = deckWordPhraseTranslationService.getPhraseTranslationsByDeckId(deckId);

        if (phrases.size() == 0) {
            throw new PhraseNotFoundException(String.format("Not found any phrase of deck %s", deckId));
        }

        Deck deck = deckService.getDeckbyId(deckId);
        return phrases.stream().map(phrase -> {
            List<WordTranslation> wordTranslations = wordPhraseTranslationService.getWordTranslationsByDeckIdPhraseTranslationId(deckId, phrase.getId());
            List<WordTranslationDTO> wordTranslationDTOs = wordTranslations.stream()
                    .map(wordTranslation -> wordTranslationMapper.toDTO(wordTranslation, deck.getCourse().getTargetLanguage()))
                    .collect(Collectors.toList());

            return new PhraseTranslationWithWordTranslationsDTO(
                    phraseTranslationMapper.toDTO(phrase, deck.getCourse().getTargetLanguage()),
                    wordTranslationDTOs
            );
        }).collect(Collectors.toList());

    }

    /**
     * Get the page pageNumber of PhraseTranslationDTO with pageSize elements of a target language
     *
     * @param pageNumber
     * @param pageSize
     * @param language
     * @return List<PhraseDTO>
     * @throws PhraseNotFoundException if not exist any Phrase
     */
    @Override
    public List<PhraseTranslationDTO> getAllTargetLanguagePhrases(Integer pageNumber, Integer pageSize, Language language) {
        logger.debug("Called getAllTargetLanguagePhrases in PhraseTranslationServiceImpl for pageNumber-{}, pageSize-{}, language-{}", pageNumber, pageSize, language);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<PhraseTranslationDTO> phrases = phraseTranslationRepository
                .findAll(pageable)
                .stream()
                .map(phraseTranslation -> phraseTranslationMapper.toDTO(phraseTranslation, language))
                .collect(Collectors.toList());

        if (phrases.isEmpty()) {
            throw new PhraseNotFoundException("Not found any phrase");
        }
        return phrases;
    }
}
