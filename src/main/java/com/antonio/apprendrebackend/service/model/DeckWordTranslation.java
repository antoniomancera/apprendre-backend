package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DeckWordTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "deck_id", nullable = false)
    private Deck deck;

    @ManyToOne
    @JoinColumn(name = "word_translation_id", nullable = false)
    private WordTranslation wordTranslation;
    private Integer baseWeight;
    private Integer currentWeight;
}
