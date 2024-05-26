package com.antonio.apprendrebackend.model;

import jakarta.persistence.*;

import java.util.List;

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

    private Integer intentos;
    private Integer aciertos;

    @OneToMany(mappedBy = "motPalabra")
    private List<MotPalabraPhrase> phrases;

    public MotPalabra() {
        this.intentos = intentos;
        this.aciertos = aciertos;
    }

    public MotPalabra(Mot mot, Palabra palabra) {
        this.mot = mot;
        this.palabra = palabra;
        this.intentos = 0;
        this.aciertos = 0;
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

    public List<MotPalabraPhrase> getPhrases() {
        return phrases;
    }

    public void setPhrases(List<MotPalabraPhrase> phrases) {
        this.phrases = phrases;
    }
}
