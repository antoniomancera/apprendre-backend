package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ConjugationVerbWordWordSense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "conjugation_verb_id")
    private ConjugationVerb conjugationVerb;

    @ManyToOne
    @JoinColumn(name = "word_id")
    private Word word;

    @OneToOne
    @JoinColumn(name = "word_sense_id")
    private WordSense wordSense;
}
