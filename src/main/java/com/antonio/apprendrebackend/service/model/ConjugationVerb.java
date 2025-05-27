package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ConjugationVerb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "verb_auxiliary_id")
    private VerbAuxiliary verbAuxiliary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verb_group_ending_id")
    private VerbGroupEnding verbGroupEnding;

    @OneToOne
    @JoinColumn(name = "word_sense_id")
    private WordSense wordSense;

    private Integer isReflexive;

}


