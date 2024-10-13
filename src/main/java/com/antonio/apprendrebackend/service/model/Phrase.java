package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;

@Entity
public class Phrase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String phraseEs;
    private String phraseFr;
    private String description;
    private Integer attempts;
    private Integer successes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhraseEs() {
        return phraseEs;
    }

    public void setPhraseEs(String phraseEs) {
        this.phraseEs = phraseEs;
    }

    public String getPhraseFr() {
        return phraseFr;
    }

    public void setPhraseFr(String phraseFr) {
        this.phraseFr = phraseFr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public Integer getSuccesses() {
        return successes;
    }

    public void setSuccesses(Integer successes) {
        this.successes = successes;
    }

    @Override
    public String toString() {
        return "Phrase{" +
                "id=" + id +
                ", phraseEs='" + phraseEs + '\'' +
                ", phraseFr='" + phraseFr + '\'' +
                ", description='" + description + '\'' +
                ", attempts=" + attempts +
                ", aciertos=" + successes +
                '}';
    }
}
