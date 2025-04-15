package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class TenseEquivalence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tense_fr_id")
    private Tense tenseFr;


    @ManyToOne
    @JoinColumn(name = "tense_sp_id")
    private Tense tenseSp;


    private String description;
}
