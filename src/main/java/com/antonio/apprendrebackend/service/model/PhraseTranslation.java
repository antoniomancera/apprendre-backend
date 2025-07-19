package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PhraseTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "phrase_fr_id")
    private Phrase phraseFr;

    @ManyToOne
    @JoinColumn(name = "phrase_sp_id")
    private Phrase phraseSp;

    private String description;
}
