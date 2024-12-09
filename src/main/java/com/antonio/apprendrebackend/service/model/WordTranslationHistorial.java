package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WordTranslationHistorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "wordTranslation_id")
    private WordTranslation wordTranslation;

    private Integer importanceIndex;

    private Long date;

    private Integer successes;

    @Column(name = "deck_id")
    private Integer deckId;

    public WordTranslationHistorial() {
        this.date = System.currentTimeMillis();
    }

    public WordTranslationHistorial(WordTranslation wordTranslation, Integer importanceIndex, Integer successes, Integer deckId) {
        this.wordTranslation = wordTranslation;
        this.importanceIndex = importanceIndex;
        this.date = System.currentTimeMillis();
        this.successes = successes;
        this.deckId = deckId;
    }

    public WordTranslationHistorial(WordTranslation wordTranslation, Integer importanceIndex, Long date, Integer successes, Integer deckId) {
        this.wordTranslation = wordTranslation;
        this.importanceIndex = importanceIndex;
        this.date = date;
        this.successes = successes;
        this.deckId = deckId;
    }
}
