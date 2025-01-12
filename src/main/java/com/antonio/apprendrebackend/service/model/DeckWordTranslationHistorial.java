package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DeckWordTranslationHistorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "wordTranslation_id")
    private WordTranslation wordTranslation;


    private Long date;

    private Integer success;

    @Column(name = "deck_id")
    private Integer deckId;

    public DeckWordTranslationHistorial() {
        this.date = System.currentTimeMillis();
    }

    public DeckWordTranslationHistorial(WordTranslation wordTranslation, Integer success, Integer deckId) {
        this.wordTranslation = wordTranslation;
        this.date = System.currentTimeMillis();
        this.success = success;
        this.deckId = deckId;
    }

    public DeckWordTranslationHistorial(WordTranslation wordTranslation, Long date, Integer success, Integer deckId) {
        this.wordTranslation = wordTranslation;
        this.date = date;
        this.success = success;
        this.deckId = deckId;
    }
}
