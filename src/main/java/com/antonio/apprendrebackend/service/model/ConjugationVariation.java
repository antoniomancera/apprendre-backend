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
public class ConjugationVariation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "conjugation_verb_id")
    private ConjugationVerb conjugationVerb;

    @ManyToOne
    @JoinColumn(name = "conjugation_irregular_pattern_id")
    private ConjugationIrregularPattern conjugationIrregularPattern;

    @OneToMany(mappedBy = "conjugationVariation", cascade = CascadeType.ALL)
    private List<ConjugationNonExist> conjugationNonExist;

    @OneToMany(mappedBy = "conjugationVariation", cascade = CascadeType.ALL)
    private List<ConjugationVerbFormIrregular> conjugationVerbFormIrregulars;
}





