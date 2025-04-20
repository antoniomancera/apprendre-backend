package com.antonio.apprendrebackend.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AttemptResultDTO {
    private boolean hasSuccess;
    private WordPhraseTranslationDTO wordPhraseTranslation;
}
