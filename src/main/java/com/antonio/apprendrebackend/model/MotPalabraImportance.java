package com.antonio.apprendrebackend.model;

import jakarta.persistence.*;

@Entity
public class MotPalabraImportance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne()
    @JoinColumn(name = "motPalabra_id")
    private MotPalabra motPalabra;
    private Integer importanceIndex;

    public MotPalabraImportance(MotPalabra motPalabra, Integer importanceIndex) {
        this.motPalabra = motPalabra;
        this.importanceIndex = importanceIndex;
    }

    public MotPalabra getMotPalabra() {
        return motPalabra;
    }

    public void setMotPalabra(MotPalabra motPalabra) {
        this.motPalabra = motPalabra;
    }

    public Integer getImportanceIndex() {
        return importanceIndex;
    }

    public void setImportanceIndex(Integer importanceIndex) {
        this.importanceIndex = importanceIndex;
    }
}
