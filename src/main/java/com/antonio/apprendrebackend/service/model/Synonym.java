package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Synonym {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "wordTranslation_id")
    private WordTranslation wordTranslation;

    @ManyToMany
    private List<Synonym> synonyms;
}
