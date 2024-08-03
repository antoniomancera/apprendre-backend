package com.antonio.apprendrebackend.service.impl;


import com.antonio.apprendrebackend.dto.PhraseDTO;
import com.antonio.apprendrebackend.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.mapper.PhraseMapper;
import com.antonio.apprendrebackend.mapper.WordTranslationMapper;
import com.antonio.apprendrebackend.model.*;
import com.antonio.apprendrebackend.repository.PhraseRepository;
import com.antonio.apprendrebackend.repository.WordTranslationPoolRepository;
import com.antonio.apprendrebackend.repository.WordTranslationRepository;
import com.antonio.apprendrebackend.service.WordTranslationPoolService;
import com.antonio.apprendrebackend.service.WordTranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


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

    @Override
    public WordTranslationDTO getRandomWordTranslation() {
        WordTranslationPool wordTranslationPool = wordTranslationPoolRepository.findRandomWordTranslationPool();

        if (wordTranslationPool == null) {
            wordTranslationPoolService.generateWordTranslationPoolEntries();
            wordTranslationPool = wordTranslationPoolRepository.findRandomWordTranslationPool();
        }

        if (wordTranslationPool == null) {
            return null;
        }

        WordTranslation wordTranslation = wordTranslationPool.getWordTranslation();

        return wordTranslationMapper.toDTO(wordTranslation);
    }

    @Override
    public WordTranslationDTO attemptsWordTranslation(int wordId, int phraseId, boolean success) {
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

        return getRandomWordTranslation();
    }

}