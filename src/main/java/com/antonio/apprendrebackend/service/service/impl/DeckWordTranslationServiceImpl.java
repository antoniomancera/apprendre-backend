package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.DeckWordTranslation;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import com.antonio.apprendrebackend.service.repository.DeckWordTranslationRespository;
import com.antonio.apprendrebackend.service.service.DeckWordTranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeckWordTranslationServiceImpl implements DeckWordTranslationService {
    @Autowired
    DeckWordTranslationRespository deckWordTranslationRespository;

    @Override
    public List<WordTranslation> getWordTranslationsByPhraseIdAndDeckId(Integer phraseId, Integer deckId) {
        List<DeckWordTranslation> deckWordTranslations = deckWordTranslationRespository.findDeckWordTranslationsByPhraseIdAndDeckId(phraseId, deckId);

        return deckWordTranslations.stream()
                .map(DeckWordTranslation::getWordTranslation)
                .collect(Collectors.toList());
    }
}
