package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class ConjugationPatronTenseFinite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "verb_pattern_irregular_id")
    private VerbPatternIrregular verbPatternIrregular;

    @ManyToOne
    @JoinColumn(name = "tense_id")
    private Tense tense;

    private String patternTermination;
}
