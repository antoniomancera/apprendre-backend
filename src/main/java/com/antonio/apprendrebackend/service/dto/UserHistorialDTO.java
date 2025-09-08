package com.antonio.apprendrebackend.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserHistorialDTO {
    private Integer id;
    private DeckWordPhraseTranslationDTO deckWordPhraseTranslation;
    private Long date;
    private SuccessDTO success;
    private Integer attempts;
    private DeckDTO deck;
}
