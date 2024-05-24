package com.antonio.apprendrebackend.model;

import jakarta.persistence.*;

public class MotPalabra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne()
    @JoinColumn(name = "mot_id")
    private Mot mot;
    @ManyToOne()
    @JoinColumn(name = "palabra_id")
    private Palabra palabra;
    @ManyToOne()
    @JoinColumn(name = "motPalabraPhrase_id")
    private MotPalabraPhrase motPalabraPhrase;
    private Integer intentos;
    private Integer aciertos;

    public MotPalabra() {
        this.intentos = intentos;
        this.aciertos = aciertos;
    }

    public MotPalabra(Mot mot, Palabra palabra, MotPalabraPhrase motPalabraPhrase) {
        this.mot = mot;
        this.palabra = palabra;
        this.motPalabraPhrase = motPalabraPhrase;
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

    public MotPalabraPhrase getMotPalabraPhrase() {
        return motPalabraPhrase;
    }

    public void setMotPalabraPhrase(MotPalabraPhrase motPalabraPhrase) {
        this.motPalabraPhrase = motPalabraPhrase;
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
