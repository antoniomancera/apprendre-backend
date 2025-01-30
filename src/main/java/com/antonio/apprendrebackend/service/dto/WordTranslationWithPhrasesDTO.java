package com.antonio.apprendrebackend.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class WordTranslationWithPhrasesDTO {
    private WordTranslationDTO wordTranslation;
    private List<PhraseDTO> phrases;
}
