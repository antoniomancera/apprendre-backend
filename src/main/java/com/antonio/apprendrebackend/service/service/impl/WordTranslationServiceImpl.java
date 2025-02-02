package com.antonio.apprendrebackend.service.service.impl;


import com.antonio.apprendrebackend.service.dto.PhraseDTO;
import com.antonio.apprendrebackend.service.dto.PhraseWithWordTranslationsDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationWithPhrasesDTO;
import com.antonio.apprendrebackend.service.exception.UserInfoNotFoundException;
import com.antonio.apprendrebackend.service.exception.WordTranslationNotFoundException;
import com.antonio.apprendrebackend.service.mapper.PhraseMapper;
import com.antonio.apprendrebackend.service.mapper.WordTranslationMapper;
import com.antonio.apprendrebackend.service.model.DeckWordTranslation;
import com.antonio.apprendrebackend.service.model.DeckWordTranslationHistorial;
import com.antonio.apprendrebackend.service.model.Phrase;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import com.antonio.apprendrebackend.service.repository.DeckWordTranslationHistorialRespository;
import com.antonio.apprendrebackend.service.repository.DeckWordTranslationRespository;
import com.antonio.apprendrebackend.service.repository.PhraseRepository;
import com.antonio.apprendrebackend.service.repository.WordTranslationRepository;
import com.antonio.apprendrebackend.service.service.PhraseService;
import com.antonio.apprendrebackend.service.service.WordTranslationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class WordTranslationServiceImpl implements WordTranslationService {
    @Autowired
    DeckWordTranslationRespository deckWordTranslationRespository;
    @Autowired
    WordTranslationRepository wordTranslationRepository;
    @Autowired
    WordTranslationMapper wordTranslationMapper;
    @Autowired
    PhraseRepository phraseRepository;
    @Autowired
    PhraseMapper phraseMapper;
    @Autowired
    DeckWordTranslationHistorialRespository deckWordTranslationHistorialRespository;


    @Autowired
    PhraseService phraseService;

    @Override
    public WordTranslationDTO getRandomWordTranslation(Integer deckId) {
        Optional<DeckWordTranslation> deckWordTranslation;
        if (deckId != null) {
            deckWordTranslation = deckWordTranslationRespository.findRandomDeckWordTranslationWithByDeck(deckId);
        } else {
            deckWordTranslation = deckWordTranslationRespository.findRandomDeckWordTranslation();
        }

        if (deckWordTranslation.isEmpty()) {
            throw new WordTranslationNotFoundException("Not found any WordTranslation");
        }

        return wordTranslationMapper.toDTO(deckWordTranslation.get().getWordTranslation());
    }

    @Override
    public WordTranslationDTO attemptsWordTranslation(int wordId, int phraseId, boolean success, Integer deckId) {
        WordTranslation wordTranslation = wordTranslationRepository.findById(wordId)
                .orElseThrow(() -> new WordTranslationNotFoundException("WordTranslation not found"));
        Phrase phrase = phraseRepository.findById(phraseId)
                .orElseThrow(() -> new WordTranslationNotFoundException("Phrase not found"));

        updateStats(success, wordTranslation, phrase);
        deckWordTranslationHistorialRespository.save(new DeckWordTranslationHistorial(wordTranslation, LocalDate.now().atStartOfDay(ZoneId.of("UTC")).toInstant().toEpochMilli(), success ? 1 : 0, deckId));

        return getRandomWordTranslation(deckId);
    }

    @Override
    public List<WordTranslationWithPhrasesDTO> getAllWordTranslationsWithPhrasesByDeck(Integer deckId) {
        Optional<List<WordTranslation>> wordTranslations = wordTranslationRepository.findWordTranslationsByDeckId(deckId);
        if (wordTranslations.isEmpty() || wordTranslations.get().size() == 0) {
            throw new WordTranslationNotFoundException("Not found any wordTranslation");
        }
        return wordTranslations.get().stream().map(word -> {
            List<Phrase> phrases = phraseService.findPhrasesByDeckIdAndWordTranslationId(deckId, word.getId());
            List<PhraseDTO> phrasesDTOs = phrases.stream()
                    .map(phraseMapper::toDTO)
                    .collect(Collectors.toList());

            return new WordTranslationWithPhrasesDTO(
                    wordTranslationMapper.toDTO(word),
                    phrasesDTOs
            );
        }).collect(Collectors.toList());
    }

    private static void updateStats(boolean success, WordTranslation wordTranslation, Phrase phrase) {
        wordTranslation.setAttempts(wordTranslation.getAttempts() + 1);
        phrase.setAttempts(phrase.getAttempts() + 1);
        if (success) {
            wordTranslation.setSuccesses(wordTranslation.getSuccesses() + 1);
            phrase.setSuccesses(phrase.getSuccesses() + 1);
        }
    }

}