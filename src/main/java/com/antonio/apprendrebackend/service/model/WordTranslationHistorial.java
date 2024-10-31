package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;

@Entity
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

    public WordTranslationHistorial() {
    }

    public WordTranslationHistorial(WordTranslation wordTranslation, Integer importanceIndex, Long date, Integer successes) {
        this.wordTranslation = wordTranslation;
        this.importanceIndex = importanceIndex;
        this.date = date;
        this.successes = successes;
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

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Integer getSuccesses() {
        return successes;
    }

    public void setSuccesses(Integer successes) {
        this.successes = successes;
    }
}
