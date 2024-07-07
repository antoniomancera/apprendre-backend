package com.antonio.apprendrebackend.service.impl;


import com.antonio.apprendrebackend.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.mapper.WordTranslationMapper;
import com.antonio.apprendrebackend.model.WordTranslation;
import com.antonio.apprendrebackend.model.WordTranslationPool;
import com.antonio.apprendrebackend.repository.WordTranslationPoolRepository;
import com.antonio.apprendrebackend.repository.WordTranslationRepository;
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
    private WordTranslationMapper wordTranslationMapper;

    @Override
    public WordTranslationDTO getRandomWordTranslationPhrase() {
        WordTranslationPool wordTranslationPool = wordTranslationPoolRepository.findRandomWordTranslationPool();

        System.out.println(
                wordTranslationPool.toString());

        if (wordTranslationPool == null) {
            return null;
        }

        WordTranslation wordTranslation = wordTranslationPool.getWordTranslation();

        if (wordTranslation == null) {
            return null;
        }

        WordTranslationDTO wordTranslationDTO = wordTranslationMapper.toDTO(wordTranslation);

        System.out.println(
                wordTranslationDTO.toString());

        return wordTranslationDTO;
    }
}