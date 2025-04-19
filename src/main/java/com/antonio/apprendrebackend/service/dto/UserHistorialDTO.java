package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.DeckUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserHistorialDTO {
    private Integer id;
    private WordTranslationDTO wordTranslation;
    private Long date;
    private Integer success;
    private Integer attempts;
    private Integer deckId;
}
