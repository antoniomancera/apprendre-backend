package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DeckWordPhraseTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "deck_id", nullable = false)
    private Deck deck;

    @ManyToOne
    @JoinColumn(name = "word_phrase_translation_id", nullable = false)
    private WordPhraseTranslation wordPhraseTranslation;

    private Integer baseWeight;
    private Integer currentWeight;
    private Integer attempts;
    private Integer successes;
}
