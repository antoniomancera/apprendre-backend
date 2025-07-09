package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.PhraseTranslationDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationWithPhraseTranslationsDTO;
import com.antonio.apprendrebackend.service.exception.WordTranslationNotFoundException;
import com.antonio.apprendrebackend.service.mapper.PhraseTranslationMapper;
import com.antonio.apprendrebackend.service.mapper.WordTranslationMapper;
import com.antonio.apprendrebackend.service.model.PhraseTranslation;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import com.antonio.apprendrebackend.service.repository.WordTranslationRepository;
import com.antonio.apprendrebackend.service.service.DeckWordPhraseTranslationService;
import com.antonio.apprendrebackend.service.service.WordPhraseTranslationService;
import com.antonio.apprendrebackend.service.service.WordTranslationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class WordTranslationServiceImpl implements WordTranslationService {
    @Autowired
    WordTranslationRepository wordTranslationRepository;
    @Autowired
    WordPhraseTranslationService wordPhraseTranslationService;
    @Autowired
    WordTranslationMapper wordTranslationMapper;

    @Autowired
    PhraseTranslationMapper phraseTranslationMapper;


    @Autowired
    DeckWordPhraseTranslationService deckWordPhraseTranslationService;

    /**
     * Get All WordTranslation and their Phrases associated given a deck
     *
     * @param deckId
     * @return List<WordTranslationWithPhrasesDTO> of a deck
     * @throws WordTranslationNotFoundException if not exist any WordTranslation of the deck
     */
    @Override
    public List<WordTranslationWithPhraseTranslationsDTO> getAllWordTranslationsWithPhrasesByDeck(Integer deckId) {
        System.out.println(deckId);
        List<WordTranslation> wordTranslations = deckWordPhraseTranslationService.getWordTranslationsByDeckId(deckId);
        if (wordTranslations.isEmpty() || wordTranslations.size() == 0) {
            throw new WordTranslationNotFoundException("Not found any wordTranslation");
        }


        return wordTranslations.stream().map(word -> {
            List<PhraseTranslation> phrases = wordPhraseTranslationService.getPhrasesByDeckIdAndWordTranslationId(deckId, word.getId());
            List<PhraseTranslationDTO> phrasesDTO = phrases.stream()
                    .map(phraseTranslationMapper::toDTO)
                    .collect(Collectors.toList());

            return new WordTranslationWithPhraseTranslationsDTO(
                    wordTranslationMapper.toDTO(word),
                    phrasesDTO
            );
        }).collect(Collectors.toList());


    }

    /**
     * Get the page pageNumber of WordTranslationDTO with pageSize elements
     *
     * @param pageNumber
     * @param pageSize
     * @return List<WordTranslationDTO>
     * @throws WordTranslationNotFoundException if not exist any WordTranslation
     */
    @Override
    public List<WordTranslationDTO> getAllWordTranslations(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<WordTranslationDTO> words = wordTranslationRepository
                .findAll(pageable)
                .stream()
                .map(wordTranslationMapper::toDTO)
                .collect(Collectors.toList());

        if (words.isEmpty()) {
            throw new WordTranslationNotFoundException("Not found any phrase");
        }
        return words;
    }
}