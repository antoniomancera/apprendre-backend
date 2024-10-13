package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;

@Entity
public class WordTranslationPhrase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "wordTranslation_id")
    private WordTranslation wordTranslation;

    @ManyToOne
    @JoinColumn(name = "phrase_id")
    private Phrase phrase;

    public Phrase getPhrase() {
        return phrase;
    }
}
