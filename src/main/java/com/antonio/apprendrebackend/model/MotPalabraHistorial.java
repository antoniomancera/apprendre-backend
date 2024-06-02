package com.antonio.apprendrebackend.model;

import jakarta.persistence.*;

@Entity
public class MotPalabraHistorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "motPalabra_id")
    private MotPalabra motPalabra;

    private Integer importance;

    public MotPalabraHistorial() {
    }

    public MotPalabraHistorial(MotPalabra motPalabra, Integer importance) {
        this.motPalabra = motPalabra;
        this.importance = importance;
    }

    public MotPalabra getMotPalabra() {
        return motPalabra;
    }

    public void setMotPalabra(MotPalabra motPalabra) {
        this.motPalabra = motPalabra;
    }

    public Integer getImportance() {
        return importance;
    }

    public void setImportance(Integer importance) {
        this.importance = importance;
    }
}
