package com.antonio.apprendrebackend.dto;

import com.antonio.apprendrebackend.model.Mot;
import com.antonio.apprendrebackend.model.MotPalabraPhrase;
import com.antonio.apprendrebackend.model.Palabra;
import com.antonio.apprendrebackend.model.Phrase;


public class MotPalabraDTO {
    private Integer id;
    private Mot mot;
    private Palabra palabra;
    private Integer attempts;
    private Integer successes;
    private PhraseDTO phrase;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Mot getMot() {
        return mot;
    }

    public void setMot(Mot mot) {
        this.mot = mot;
    }

    public Palabra getPalabra() {
        return palabra;
    }

    public void setPalabra(Palabra palabra) {
        this.palabra = palabra;
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

    public PhraseDTO getPhrase() {
        return phrase;
    }

    public void setPhrase(PhraseDTO phrase) {
        this.phrase = phrase;
    }

    @Override
    public String toString() {
        return "MotPalabraDTO{" +
                "id=" + id +
                ", mot=" + mot +
                ", palabra=" + palabra +
                ", attempts=" + attempts +
                ", successes=" + successes +
                ", phrase=" + phrase.toString() +
                '}';
    }
}
