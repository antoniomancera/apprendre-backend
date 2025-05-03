package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.WordPhraseTranslation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeckWordPhraseTranslationDTO {
    private Integer id;
    private DeckDTO deck;
    private WordPhraseTranslation wordPhraseTranslation;
}
