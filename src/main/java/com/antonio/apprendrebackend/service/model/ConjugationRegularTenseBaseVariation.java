package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ConjugationRegularTenseBaseVariation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "tense_id")
    private Tense tense;

    @ManyToOne
    @JoinColumn(name = "conjugation_verb_form_base_id")
    private ConjugationVerbForm conjugationVerbFormBase;

    private Boolean isInfinitiveBase;

    private Boolean isEndingInBase;
}
