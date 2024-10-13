package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;

public interface WordTranslationService {
    WordTranslationDTO getRandomWordTranslation();

    WordTranslationDTO attemptsWordTranslation(int wordId, int phraseId, boolean success);
}