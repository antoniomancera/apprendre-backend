package com.antonio.apprendrebackend.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhraseTranslationDTO {
    private Integer id;
    private PhraseDTO phraseTarget;
    private PhraseDTO phraseBase;
}