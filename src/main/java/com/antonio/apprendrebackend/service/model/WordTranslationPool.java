package com.antonio.apprendrebackend.service.model;


import jakarta.persistence.*;

@Entity
public class WordTranslationPool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "wordTranslation_id", nullable = false)
    private WordTranslation wordTranslation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public WordTranslationPool() {
    }

    public WordTranslationPool(WordTranslation wordTranslation) {
        this.wordTranslation = wordTranslation;
    }

    public WordTranslation getWordTranslation() {
        return wordTranslation;
    }

    public void setWordTranslation(WordTranslation wordTranslation) {
        this.wordTranslation = wordTranslation;
    }
}

