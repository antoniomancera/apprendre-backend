package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.WordTranslation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PhraseWithWordTranslationsDTO {
    private PhraseDTO Phrase;
    private List<WordTranslationDTO> wordTranslations;
}
