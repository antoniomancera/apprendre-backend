package com.antonio.apprendrebackend.model;

import jakarta.persistence.*;

@Entity
public class Phrase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String phraseEs;
    private String phraseFr;
    private String description;
    private Integer intentos;
    private Integer aciertos;

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

    @Override
    public String toString() {
        return "Phrase{" +
                "id=" + id +
                ", phraseEs='" + phraseEs + '\'' +
                ", phraseFr='" + phraseFr + '\'' +
                ", description='" + description + '\'' +
                ", intentos=" + intentos +
                ", aciertos=" + aciertos +
                '}';
    }
}
