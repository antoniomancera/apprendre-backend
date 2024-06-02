package com.antonio.apprendrebackend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Synonym {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "motPalabra_id")
    private MotPalabra motPalabra;

    @ManyToMany
    private List<Synonym> synonyms;
}
