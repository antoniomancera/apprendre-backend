package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.DeckUser;
import com.antonio.apprendrebackend.service.model.WordPhraseTranslation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeckUserWordPhraseTranslationDTO {
    private Integer id;
    private DeckUserDTO deckUser;
    private WordPhraseTranslation wordPhraseTranslation;
}
