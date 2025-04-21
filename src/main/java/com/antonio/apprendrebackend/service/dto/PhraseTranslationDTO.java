package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.Phrase;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhraseTranslationDTO {
    private Integer id;
    private String phraseFr;
    private String phraseSp;
}