package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.DeckWordTranslationHistorial;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DeckWordTranslationHistorialDTO {
    private Integer id;
    private WordTranslationDTO wordTranslation;
    private Long date;
    private Integer success;
    private Integer attempts;
    private Integer deckId;
}
