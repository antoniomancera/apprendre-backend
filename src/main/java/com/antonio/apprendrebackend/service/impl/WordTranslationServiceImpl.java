package com.antonio.apprendrebackend.service.impl;


import com.antonio.apprendrebackend.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.mapper.WordTranslationMapper;
import com.antonio.apprendrebackend.model.WordTranslation;
import com.antonio.apprendrebackend.model.WordTranslationPool;
import com.antonio.apprendrebackend.repository.WordTranslationPoolRepository;
import com.antonio.apprendrebackend.repository.WordTranslationRepository;
import com.antonio.apprendrebackend.service.WordTranslationPoolService;
import com.antonio.apprendrebackend.service.WordTranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
}