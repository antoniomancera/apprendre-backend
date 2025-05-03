package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.PhraseTranslationDTO;
import com.antonio.apprendrebackend.service.dto.PhraseTranslationWithWordTranslationsDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.exception.PhraseNotFoundException;
import com.antonio.apprendrebackend.service.mapper.PhraseTranslationMapper;
import com.antonio.apprendrebackend.service.mapper.WordTranslationMapper;
import com.antonio.apprendrebackend.service.model.PhraseTranslation;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import com.antonio.apprendrebackend.service.repository.PhraseTranslationRepository;
import com.antonio.apprendrebackend.service.service.DeckWordPhraseTranslationService;
import com.antonio.apprendrebackend.service.service.PhraseTranslationService;
import com.antonio.apprendrebackend.service.service.WordPhraseTranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhraseTranslationServiceImpl implements PhraseTranslationService {
    @Autowired
    PhraseTranslationRepository phraseTranslationRepository;

    @Autowired
    WordPhraseTranslationService wordPhraseTranslationService;

    @Autowired
    DeckWordPhraseTranslationService deckWordPhraseTranslationService;

    @Autowired
    WordTranslationMapper wordTranslationMapper;

    @Autowired
    PhraseTranslationMapper phraseTranslationMapper;

    /**
     * Get All Phrases and their WordTranslation of a deck
     *
     * @param deckId
     * @return List<PhraseTranslationWithWordTranslationsDTO> of a deck
     * @throws PhraseNotFoundException if not exist any Phrase of the deck
     */
    @Override
    public List<PhraseTranslationWithWordTranslationsDTO> getAllPhrasesWithWordTranslationsByDeck(Integer deckId) {
        List<PhraseTranslation> phrases = deckWordPhraseTranslationService.getPhraseTranslationsByDeckId(deckId);

        if (phrases.size() == 0) {
            throw new PhraseNotFoundException(String.format("Not found any phrase of deck %s", deckId));
        }

        return phrases.stream().map(phrase -> {
            List<WordTranslation> wordTranslations = wordPhraseTranslationService.getWordTranslationsByDeckIdPhraseTranslationId(deckId, phrase.getId());
            List<WordTranslationDTO> wordTranslationDTOs = wordTranslations.stream()
                    .map(wordTranslationMapper::toDTO)
                    .collect(Collectors.toList());

            return new PhraseTranslationWithWordTranslationsDTO(
                    phraseTranslationMapper.toDTO(phrase),
                    wordTranslationDTOs
            );
        }).collect(Collectors.toList());

    }

    /**
     * Get the page pageNumber of PhraseTranslationDTO with pageSize elements
     *
     * @param pageNumber
     * @param pageSize
     * @return List<PhraseDTO>
     * @throws PhraseNotFoundException if not exist any Phrase
     */
    @Override
    public List<PhraseTranslationDTO> getAllPhrases(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<PhraseTranslationDTO> phrases = phraseTranslationRepository
                .findAll(pageable)
                .stream()
                .map(phraseTranslationMapper::toDTO)
                .collect(Collectors.toList());

        if (phrases.isEmpty()) {
            throw new PhraseNotFoundException("Not found any phrase");
        }
        return phrases;
    }
}
