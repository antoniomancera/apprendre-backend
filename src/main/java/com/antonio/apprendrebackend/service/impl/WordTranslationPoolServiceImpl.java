package com.antonio.apprendrebackend.service.impl;

import com.antonio.apprendrebackend.model.WordTranslation;
import com.antonio.apprendrebackend.model.WordTranslationPool;
import com.antonio.apprendrebackend.repository.WordTranslationPoolRepository;
import com.antonio.apprendrebackend.repository.WordTranslationRepository;
import com.antonio.apprendrebackend.service.WordTranslationPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WordTranslationPoolServiceImpl implements WordTranslationPoolService {
    @Autowired
    WordTranslationRepository wordTranslationRepository;
    @Autowired
    WordTranslationPoolRepository wordTranslationPoolRepository;

    @Override
    public void generateWordTranslationPoolEntries() {
        wordTranslationPoolRepository.deleteAll();
        List<WordTranslation> wordTranslationList = wordTranslationRepository.findAll();
        List<WordTranslationPool> wordTranslationPoolEntries = new ArrayList<>();
        for (WordTranslation word : wordTranslationList) {
            for (int i = 0; i < word.getImportanceIndex(); i++) {
                wordTranslationPoolEntries.add(new WordTranslationPool(word));
            }
        }

        wordTranslationPoolRepository.saveAll(wordTranslationPoolEntries);
    }
}
