package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WordTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "word_sense_fr_id")
    private WordSense wordSenseFr;

    @ManyToOne
    @JoinColumn(name = "word_sense_sp_id")
    private WordSense wordSenseSp;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    @ManyToOne
    @JoinColumn(name = "conjugation_verb_form_id")
    private ConjugationVerbForm conjugationVerbForm;

    private Integer attempts;
    private Integer successes;
    private Integer importanceIndex;


    public WordTranslation() {
        this.attempts = 0;
        this.successes = 0;
    }

    public WordTranslation(WordSense wordSenseFr, WordSense wordSenseSp) {
        this.wordSenseFr = wordSenseFr;
        this.wordSenseSp = wordSenseSp;
        this.attempts = 0;
        this.successes = 0;
    }
}
