package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
public class ConjugationVariation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "conjugation_non_exist_id")
    private ConjugationNonExist conjugationNonExist;

    @ManyToOne
    @JoinColumn(name = "conjugation_irregular_pattern_id")
    private ConjugationIrregularPattern conjugationIrregularPattern;

    @OneToMany(mappedBy = "conjugationVariation", cascade = CascadeType.ALL)
    private List<ConjugationVerbFormIrregular> conjugationVerbFormIrregulars;

    @OneToOne
    @JoinColumn(name = "conjugation_verb_id")
    private ConjugationVerb conjugationVerb;
}





