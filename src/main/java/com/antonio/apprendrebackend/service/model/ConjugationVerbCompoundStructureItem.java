package com.antonio.apprendrebackend.service.model;

import com.antonio.apprendrebackend.service.util.AuxiliaryPrincipalVerbEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ConjugationVerbCompoundStructureItem {
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
    private AuxiliaryPrincipalVerbEnum auxiliarPrincipalVerb;

    private Integer position;
}
