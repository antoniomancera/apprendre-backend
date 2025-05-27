package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ConjugationNonExist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Conjugation_variation_id")
    private ConjugationVariation conjugationVariation;

    @ManyToOne
    @JoinColumn(name = "conjugation_verb_form_id")
    private ConjugationVerbForm conjugationVerbForm;
}
