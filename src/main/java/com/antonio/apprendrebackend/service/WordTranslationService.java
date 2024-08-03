package com.antonio.apprendrebackend.service;

import com.antonio.apprendrebackend.dto.WordTranslationDTO;

public interface WordTranslationService {
    WordTranslationDTO getRandomWordTranslation();

    WordTranslationDTO attemptsWordTranslation(int wordId, int phraseId, boolean success);
}