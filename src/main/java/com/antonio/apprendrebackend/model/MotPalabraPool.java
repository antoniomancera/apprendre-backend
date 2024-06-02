package com.antonio.apprendrebackend.model;


import jakarta.persistence.*;

@Entity
public class MotPalabraPool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "motPalabra_id", nullable = false)
    private MotPalabra motPalabra;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MotPalabra getMotPalabra() {
        return motPalabra;
    }

    public void setMotPalabra(MotPalabra motPalabra) {
        this.motPalabra = motPalabra;
    }
}

