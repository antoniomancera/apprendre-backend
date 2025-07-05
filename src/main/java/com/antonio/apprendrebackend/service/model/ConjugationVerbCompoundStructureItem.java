package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ConjugationVerbCompoundStructureItem {
    public enum AuxiliarPrincipalVerb {
        AUXILIAR,
        PRINCIPAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "conjugation_verb_non_simple_structure_id")
    private ConjugationVerbCompoundStructure conjugationVerbCompoundStructure;

    @ManyToOne
    @JoinColumn(name = "word_sense_id")
    private WordSense wordSense;

    @ManyToOne
    @JoinColumn(name = "tense_id")
    private Tense tense;

    @ManyToOne
    @JoinColumn(name = "conjugation_verb_form_id")
    private ConjugationVerbForm conjugationVerbForm;

    @Enumerated(EnumType.STRING)
    private AuxiliarPrincipalVerb auxiliarPrincipalVerb;

    private Integer position;
}
