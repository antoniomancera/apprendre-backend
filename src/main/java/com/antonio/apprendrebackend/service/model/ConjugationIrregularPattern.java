package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
public class ConjugationIrregularPattern {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @OneToMany
    @JoinColumn(name = "conjugation_derivation_patron_tense_id")
    private List<ConjugationVerbForm> conjugationDerivationPatronTense;


    @ManyToOne
    @JoinColumn(name = "conjugation_patron_tense_finite_id")
    private ConjugationPatronTenseFinite conjugationPatronTenseFinite;

    @ManyToOne
    @JoinColumn(name = "conjugation_patron_tense_non_finite_id")
    private ConjugationPatronTenseNonFinite conjugationPatronTenseNonFinite;

}


