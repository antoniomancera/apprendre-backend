package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.PhraseTranslation;
import com.antonio.apprendrebackend.service.model.WordTranslation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordPhraseTranslationDTO {
    private Integer id;
    private WordTranslationDTO wordTranslation;
    private PhraseTranslationDTO phraseTranslation;
}
