package com.antonio.apprendrebackend.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Random;

@Entity
public class MotPalabra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "mot_id")
    private Mot mot;

    @ManyToOne
    @JoinColumn(name = "palabra_id")
    private Palabra palabra;
    private Integer attempts;
    private Integer successes;
    private Integer importanceIndex;

    @OneToMany(mappedBy = "motPalabra")
    private List<MotPalabraPhrase> phrases;

    public MotPalabra() {
        this.attempts = 0;
        this.successes = 0;
    }

    public MotPalabra(Mot mot, Palabra palabra) {
        this.mot = mot;
        this.palabra = palabra;
        this.attempts = 0;
        this.successes = 0;
    }

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

    public Integer getImportanceIndex() {
        return importanceIndex;
    }

    public void setImportanceIndex(Integer importanceIndex) {
        this.importanceIndex = importanceIndex;
    }

    public List<MotPalabraPhrase> getPhrases() {
        return phrases;
    }

    public void setPhrases(List<MotPalabraPhrase> phrases) {
        this.phrases = phrases;
    }

    @Override
    public String toString() {
        return "MotPalabra{" +
                "id=" + id +
                ", mot=" + mot +
                ", palabra=" + palabra +
                ", attempts=" + attempts +
                ", successes=" + successes +
                ", importanceIndex=" + importanceIndex +
                ", phrases=" + phrases +
                '}';
    }

    public MotPalabraPhrase getRandomPhrase() {
        if (phrases == null || phrases.isEmpty()) {
            return null;
        }
        Random rand = new Random();
        return phrases.get(rand.nextInt(phrases.size()));
    }
}
