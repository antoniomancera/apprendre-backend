package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
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
    }

    public WordTranslationHistorial(WordTranslation wordTranslation, Integer importanceIndex, Long date, Integer successes, Integer deckId) {
        this.wordTranslation = wordTranslation;
        this.importanceIndex = importanceIndex;
        this.date = date;
        this.successes = successes;
        this.deckId = deckId;
    }
}
