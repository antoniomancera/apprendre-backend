package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class ConjugationVerb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "verb_auxiliary_id")
    private VerbAuxiliary verbAuxiliary;

    @ManyToOne
    @JoinColumn(name = "verb_group_id")
    private VerbGroup verbGroup;

    @OneToOne
    @JoinColumn(name = "conjugation_variation_id")
    private ConjugationVariation conjugationVariation;

    @OneToOne
    @JoinColumn(name = "word_sense_id")
    private WordSense wordSense;

    private String participle;
    private String gerund;
    private Integer isReflexive;

}


