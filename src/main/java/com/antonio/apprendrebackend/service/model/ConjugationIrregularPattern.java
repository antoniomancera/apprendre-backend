package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ConjugationIrregularPattern {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "conjugation_patron_tense_finite_id")
    private ConjugationPatronTenseFinite conjugationPatronTenseFinite;

    @ManyToOne
    @JoinColumn(name = "conjugation_patron_tense_non_finite_id")
    private ConjugationPatronTenseNonFinite conjugationPatronTenseNonFinite;

}


