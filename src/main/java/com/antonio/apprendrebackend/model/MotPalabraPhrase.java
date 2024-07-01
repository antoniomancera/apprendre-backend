package com.antonio.apprendrebackend.model;

import jakarta.persistence.*;

@Entity
public class MotPalabraPhrase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "mot_palabra_id")
    private MotPalabra motPalabra;

    @ManyToOne
    @JoinColumn(name = "phrase_id")
    private Phrase phrase;

    public Phrase getPhrase() {
        return phrase;
    }
}
