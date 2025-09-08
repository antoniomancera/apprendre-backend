package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private Long beginDate;
    private Long endDate;

    public DeckWordPhraseTranslation(Deck deck, WordPhraseTranslation wordPhraseTranslation) {
        this.deck = deck;
        this.wordPhraseTranslation = wordPhraseTranslation;
        this.baseWeight = wordPhraseTranslation.getWordTranslation().getBaseWeight();
        this.currentWeight = this.baseWeight;
        this.beginDate = System.currentTimeMillis();
        this.attempts = 0;
        this.successes = 0;
    }
}
