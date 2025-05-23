package com.antonio.apprendrebackend.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhraseTranslationWithWordTranslationsDTO {
    private PhraseTranslationDTO phrase;
    private List<WordTranslationDTO> wordTranslations;
}
