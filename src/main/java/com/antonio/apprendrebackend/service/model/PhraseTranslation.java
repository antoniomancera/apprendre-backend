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
    @JoinColumn(name = "phrase_a_id")
    private Phrase phraseA;

    @ManyToOne
    @JoinColumn(name = "phrase_b_id")
    private Phrase phraseB;

    private String description;

    private Integer baseWeight;
}
