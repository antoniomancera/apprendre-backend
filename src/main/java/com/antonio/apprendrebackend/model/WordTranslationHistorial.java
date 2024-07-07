package com.antonio.apprendrebackend.model;

import jakarta.persistence.*;

@Entity
public class WordTranslationHistorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "wordTranslation_id")
    private WordTranslation wordTranslation;

    private Integer importance;

    public WordTranslationHistorial() {
    }

    public WordTranslationHistorial(WordTranslation wordTranslation, Integer importance) {
        this.wordTranslation = wordTranslation;
        this.importance = importance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public WordTranslation getWordTranslation() {
        return wordTranslation;
    }

    public void setWordTranslation(WordTranslation wordTranslation) {
        this.wordTranslation = wordTranslation;
    }

    public Integer getImportance() {
        return importance;
    }

    public void setImportance(Integer importance) {
        this.importance = importance;
    }
}
