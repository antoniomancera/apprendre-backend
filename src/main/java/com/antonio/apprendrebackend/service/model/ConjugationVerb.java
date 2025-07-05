package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ConjugationVerb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "verb_auxiliary_id")
    private VerbAuxiliary verbAuxiliary;

    @ManyToOne
    @JoinColumn(name = "verb_group_ending_id")
    private VerbGroupEnding verbGroupEnding;

    @ManyToOne
    @JoinColumn(name = "word_id")
    private Word word;

    @OneToOne
    @JoinColumn(name = "word_sense_id")
    private WordSense wordSense;

    private Integer isReflexive;
}


