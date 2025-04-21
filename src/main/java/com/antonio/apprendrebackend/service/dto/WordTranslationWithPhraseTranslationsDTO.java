package com.antonio.apprendrebackend.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class WordTranslationWithPhraseTranslationsDTO {
    private WordTranslationDTO wordTranslation;
    private List<PhraseTranslationDTO> phraseTranslations;
}
