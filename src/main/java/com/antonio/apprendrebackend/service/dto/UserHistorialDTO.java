package com.antonio.apprendrebackend.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserHistorialDTO {
    private Integer id;
    private DeckUserWordPhraseTranslationDTO deckUserWordPhraseTranslation;
    private Long date;
    private Integer success;
    private Integer attempts;
    private Integer deckId;
}
