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
}
