package com.antonio.apprendrebackend.service.service.impl;


import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.mapper.PhraseMapper;
import com.antonio.apprendrebackend.service.mapper.WordTranslationMapper;
import com.antonio.apprendrebackend.service.model.Phrase;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import com.antonio.apprendrebackend.service.model.WordTranslationHistorial;
import com.antonio.apprendrebackend.service.model.WordTranslationPool;
import com.antonio.apprendrebackend.service.repository.PhraseRepository;
import com.antonio.apprendrebackend.service.repository.WordTranslationHistorialRepository;
import com.antonio.apprendrebackend.service.repository.WordTranslationPoolRepository;
import com.antonio.apprendrebackend.service.repository.WordTranslationRepository;
import com.antonio.apprendrebackend.service.service.WordTranslationPoolService;
import com.antonio.apprendrebackend.service.service.WordTranslationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;


@Service
public class WordTranslationServiceImpl implements WordTranslationService {
    @Autowired
    WordTranslationPoolRepository wordTranslationPoolRepository;
    @Autowired
    WordTranslationRepository wordTranslationRepository;
    @Autowired
    WordTranslationMapper wordTranslationMapper;
    @Autowired
    WordTranslationPoolService wordTranslationPoolService;
    @Autowired
    PhraseRepository phraseRepository;
    @Autowired
    PhraseMapper phraseMapper;

    @Autowired
    WordTranslationHistorialRepository wordTranslationHistorialRepository;

    @Override
    public WordTranslationDTO getRandomWordTranslation(Integer deckId) {
        WordTranslationPool wordTranslationPool = new WordTranslationPool();
        if (deckId != null) {
            wordTranslationPool = wordTranslationPoolRepository.findRandomWordTranslationPoolWithByDeck(deckId);
        } else {
            wordTranslationPool = wordTranslationPoolRepository.findRandomWordTranslationPool();
        }

        if (wordTranslationPool == null) {
            return null;
        }

        WordTranslation wordTranslation = wordTranslationPool.getWordTranslation();

        return wordTranslationMapper.toDTO(wordTranslation);
    }

    @Override
    public WordTranslationDTO attemptsWordTranslation(int wordId, int phraseId, boolean success, Integer deckId) {
        WordTranslation wordTranslation = wordTranslationRepository.findById(wordId).get();
        Phrase phrase = phraseRepository.findById(phraseId).get();

        if (wordTranslation == null || phrase == null) {
            return null;
        }

        wordTranslation.setAttempts(wordTranslation.getAttempts() + 1);
        phrase.setAttempts(phrase.getAttempts() + 1);
        if (success) {
            wordTranslation.setSuccesses(wordTranslation.getSuccesses() + 1);
            phrase.setSuccesses(phrase.getSuccesses() + 1);
        }
        wordTranslationRepository.save(wordTranslation);
        phraseRepository.save(phrase);
        wordTranslationHistorialRepository.save(new WordTranslationHistorial(wordTranslation, wordTranslation.getImportanceIndex(), LocalDate.now().atStartOfDay(ZoneId.of("UTC")).toInstant().toEpochMilli(), success ? 1 : 0, deckId));

        return getRandomWordTranslation(deckId);
    }

}