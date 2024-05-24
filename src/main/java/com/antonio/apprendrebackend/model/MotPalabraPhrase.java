package com.antonio.apprendrebackend.model;

import jakarta.persistence.*;

public class MotPalabraPhrase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String phraseEs;
    private String phraseFr;
    @ManyToOne()
    @JoinColumn(name = "type_id")
    private Type type;
    private String description;
    private Integer intentos;
    private Integer aciertos;

    public MotPalabraPhrase() {
        this.intentos = 0;
        this.aciertos = 0;
    }

    public MotPalabraPhrase(String phraseEs, String phraseFr, Type type) {
        this.phraseEs = phraseEs;
        this.phraseFr = phraseFr;
        this.type = type;
        this.intentos = 0;
        this.aciertos = 0;
    }

    public MotPalabraPhrase(String phraseEs, String phraseFr, Type type, String description) {
        this.phraseEs = phraseEs;
        this.phraseFr = phraseFr;
        this.type = type;
        this.description = description;
        this.intentos = 0;
        this.aciertos = 0;
    }

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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIntentos() {
        return intentos;
    }

    public void setIntentos(Integer intentos) {
        this.intentos = intentos;
    }

    public Integer getAciertos() {
        return aciertos;
    }

    public void setAciertos(Integer aciertos) {
        this.aciertos = aciertos;
    }
}
