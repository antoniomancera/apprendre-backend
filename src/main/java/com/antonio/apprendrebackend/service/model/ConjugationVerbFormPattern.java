package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class ConjugationVerbFormPattern {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "conjugation_verb_form_id")
    private ConjugationVerbForm conjugationVerbForm;

    @ManyToOne
    @JoinColumn(name = "verb_pattern_irregular_id")
    private VerbPatternIrregular verbPatternIrregular;
}
